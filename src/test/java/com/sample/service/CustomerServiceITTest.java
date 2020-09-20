package com.sample.service;

import com.sample.db.entity.Customer;
import com.sample.db.repository.ReactiveCustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@SpringBootTest
public class CustomerServiceITTest {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private ReactiveCustomerRepository reactiveCustomerRepository;

    @Test
    public void create_expectCustomerToBeSaved() {
        // GIVEN
        final String emailAddress = "email@email.com";
        final String password = "pwd";
        final String role = "tester";

        // WHEN
        Mono<Customer> customerMono = customerService.create(emailAddress, password, role);

        // THEN
        StepVerifier
                .create(customerMono)
                .expectNextMatches(saved -> saved.getEmail().equals(emailAddress) &&
                        saved.getPassword().equals(password) &&
                        saved.getRole().equals(role) &&
                        saved.getId() == 5) // We expect 5 as 4 customers are stored previously by SampleDataInitializer.
                .verifyComplete();
    }

    @Test
    public void findAll_saveThreeCustomers_expectTheseBack() {
        // GIVEN
        Flux<Customer> saved = reactiveCustomerRepository.saveAll(Flux.just(
                Customer.builder().email("a@gmail.com").password("a").role("testerA").build(),
                Customer.builder().email("b@gmail.com").password("b").role("testerB").build(),
                Customer.builder().email("c@gmail.com").password("c").role("testerC").build()));
        saved.blockLast();  // Required so the 3 new customers have the time to be saved before findAll() is called on customerService.

        List<Customer> expectedCustomers = new ArrayList<>();
        expectedCustomers.add(Customer.builder().id(1L).email("A@email.com").password("tester123").role("Tester").build());
        expectedCustomers.add(Customer.builder().id(2L).email("B@email.com").password("tester123").role("Tester").build());
        expectedCustomers.add(Customer.builder().id(3L).email("C@email.com").password("tester123").role("Tester").build());
        expectedCustomers.add(Customer.builder().id(4L).email("D@email.com").password("tester123").role("Tester").build());
        expectedCustomers.add(Customer.builder().id(5L).email("a@gmail.com").password("a").role("testerA").build());
        expectedCustomers.add(Customer.builder().id(6L).email("b@gmail.com").password("b").role("testerB").build());
        expectedCustomers.add(Customer.builder().id(7L).email("c@gmail.com").password("c").role("testerC").build());
        Predicate<Customer> match = customer -> expectedCustomers.contains(customer);

        // WHEN
        Flux<Customer> foundCustomers = customerService.findAll();

        // THEN
        Long nbOfSavedCustomers = foundCustomers.count().block();
        assertEquals(7, nbOfSavedCustomers);    // 4 from SampleDataInitializer + 3 from our GIVEN

        // TODO More elegant way than these 7 expectNextMatches
        // TODO Better assertions to verify that we really get the full 7 expected items. Currently, we good get 7 times the id=1 and it would pass.
        StepVerifier.create(foundCustomers)
                .expectNextMatches(match)
                .expectNextMatches(match)
                .expectNextMatches(match)
                .expectNextMatches(match)
                .expectNextMatches(match)
                .expectNextMatches(match)
                .expectNextMatches(match)
                .verifyComplete();
    }
}

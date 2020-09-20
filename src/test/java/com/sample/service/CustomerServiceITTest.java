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

        Predicate<Customer> match = customer -> saved.any(saveItem -> saveItem.equals(customer)).block();

        // WHEN
        Flux<Customer> foundCustomers = customerService.findAll();

        // THEN
        Long nbOfSavedCustomers = foundCustomers.count().block();
        assertEquals(7, nbOfSavedCustomers);    // 4 from SampleDataInitializer + 3 from our GIVEN

        /**
         * TODO The below was what was found in the tutorial. It does not seem to be a good test though.
         * TODO The 4 on L59 is our 4 customers stored previously by SampleDataInitializer. Instead, we want to assert
         * TODO on the ones created at L49.
         *
         * TODO Check what the thenMany really does as it seems that the test returns saved on findAll() to then assert
         * TODO that it is saved. Rather pointless. Instead, we should ensure that the saving done on L49 does work.
         */

//        Flux<Customer> composite = customerService.findAll().thenMany(saved);

        // THEN
//        StepVerifier
//                .create(composite)
//                .expectNextMatches(match)
//                .expectNextMatches(match)
//                .expectNextMatches(match)
//                .verifyComplete();
    }
}

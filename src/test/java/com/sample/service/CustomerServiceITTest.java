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
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS;

@DirtiesContext(classMode = BEFORE_CLASS)
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
        Predicate<Customer> match = customer -> saved.any(saveItem -> saveItem.equals(customer)).block();

        // WHEN
        Mono<Long> composite = customerService.findAll().count();

        Long nbSavedCustomers = composite.block();
        assertEquals(4, nbSavedCustomers);

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

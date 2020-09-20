package com.sample.service;

import com.sample.db.entity.Customer;
import com.sample.db.repository.ReactiveCustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Predicate;

@SpringBootTest
@Import(CustomerService.class)
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
                        saved.getId() > 0)
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
        Flux<Customer> composite = customerService.findAll().thenMany(saved);

        // THEN
        StepVerifier
                .create(composite)
                .expectNextMatches(match)
                .expectNextMatches(match)
                .expectNextMatches(match)
                .verifyComplete();
    }
}

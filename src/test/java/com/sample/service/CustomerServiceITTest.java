package com.sample.service;

import com.sample.db.entity.Customer;
import com.sample.db.repository.ReactiveCustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@Import(CustomerService.class)
public class CustomerServiceITTest {
    private final CustomerService customerService;
    private final ReactiveCustomerRepository reactiveCustomerRepository;

    public CustomerServiceITTest(@Autowired CustomerService customerService, @Autowired ReactiveCustomerRepository reactiveCustomerRepository) {
        this.customerService = customerService;
        this.reactiveCustomerRepository = reactiveCustomerRepository;
    }

    @Test
    public void save() {
        // GIVEN
        final String emailAddress = "email@email.com";
        final String password = "pwd";
        final String role = "tester";

        // WHEN
        Mono<Customer> customerMono = this.customerService.create(emailAddress, password, role);

        // THEN
        StepVerifier
                .create(customerMono)
                .expectNextMatches(saved -> saved.getEmail().equals(emailAddress) &&
                        saved.getPassword().equals(password) &&
                        saved.getRole().equals(role) &&
                        saved.getId() > 0)
                .verifyComplete();
    }
}

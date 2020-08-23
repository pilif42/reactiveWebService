package com.sample.service;

import com.sample.db.entity.Customer;
import com.sample.db.repository.ReactiveCustomerRepository;
import com.sample.event.CustomerCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {
    private final ReactiveCustomerRepository reactiveCustomerRepository;
    private final ApplicationEventPublisher publisher;

    public CustomerService(ReactiveCustomerRepository reactiveCustomerRepository, ApplicationEventPublisher publisher) {
        this.reactiveCustomerRepository = reactiveCustomerRepository;
        this.publisher = publisher;
    }

    public Flux<Customer> findAll() {
        return reactiveCustomerRepository.findAll();
    }

    public Mono<Customer> create(String email, String password, String role) {
        return reactiveCustomerRepository
                .save(Customer.builder().email(email).role(role).password(password).build())
                .doOnSuccess(profile -> publisher.publishEvent(new CustomerCreatedEvent(profile)));
    }
}

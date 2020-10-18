package com.sample.service;

import com.sample.db.entity.Customer;
import com.sample.db.repository.ReactiveCustomerRepository;
import com.sample.dto.CustomerDto;
import com.sample.event.CustomerCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

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

    public Mono<Customer> findOne(Long id) {
        return reactiveCustomerRepository.findById(id);
    }

    public Mono<Customer> create(CustomerDto customerDto) {
        return reactiveCustomerRepository
                .save(Customer.builder()
                        .email(customerDto.getEmail())
                        .role(customerDto.getRole())
                        .password(customerDto.getPassword())
                        .build())
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(20))
                .filter(e -> e instanceof DataAccessException))
                .doOnSuccess(customer -> publisher.publishEvent(new CustomerCreatedEvent(customer)));
    }
}

package com.sample.controller;

import com.sample.db.entity.Customer;
import com.sample.dto.CustomerDto;
import com.sample.exception.CustomerNotFoundException;
import com.sample.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static java.lang.String.format;

@AllArgsConstructor
@RestController
@RequestMapping("/annotatedCustomers")
public class CustomerController {
    private static Function<Customer, CustomerDto> toCustomerDto = customer -> {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setEmail(customer.getEmail());
        customerDto.setRole(customer.getRole());
        customerDto.setPassword(customer.getPassword());
        return customerDto;
    };

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        Mono<Customer> response = customerService.create(customerDto);
        return response.map(toCustomerDto);
    }

    @GetMapping
    public Flux<CustomerDto> getAllCustomers() {
        return customerService.findAll().map(toCustomerDto);
    }

    @GetMapping("/{id}")
    public Mono<CustomerDto> getOneCustomer(@PathVariable("id") Long id) {
        return customerService.findOne(id).map(toCustomerDto)
                .switchIfEmpty(Mono.error(new CustomerNotFoundException(format("No customer found for id %d", id))));
    }
}

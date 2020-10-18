package com.sample.controller;

import com.sample.db.entity.Customer;
import com.sample.dto.CustomerDto;
import com.sample.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Function;

@AllArgsConstructor
@RestController
@RequestMapping("/annotatedCustomers")
public class CustomerController {
    private static Function<Customer, CustomerDto> customerMapper = customer -> {
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
    public Long createCustomer(@RequestBody CustomerDto customerDto) {
        Mono<Customer> response = customerService.create(customerDto);
        Mono<ServerResponse> resultTemp = Mono.from(response).flatMap(customer -> ServerResponse
                .created(URI.create("/customers/" + customer.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .build()
        );
        ServerResponse serverResponse = resultTemp.block();

        Long result = 5l;
        return result;
    }

    @GetMapping
    public Flux<CustomerDto> getAllCustomers() {
        return customerService.findAll().map(customerMapper);
    }

    @GetMapping("/{id}")
    public Mono<CustomerDto> getOneCustomer(@PathVariable("id") Long id) {
        return customerService.findOne(id).map(customerMapper);
    }
}

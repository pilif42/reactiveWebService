package com.sample.handler;

import com.sample.db.entity.Customer;
import com.sample.dto.CustomerDto;
import com.sample.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@Component
public class CustomerHandler {
    private final CustomerService customerService;

    public CustomerHandler(CustomerService customerService) {
        this.customerService = customerService;
    }

    public Mono<ServerResponse> createCustomer(ServerRequest request) {
        Flux<Customer> flux = request
                .bodyToFlux(CustomerDto.class)
                .flatMap(customerDto -> customerService.create(customerDto.getEmail(), customerDto.getPassword(), customerDto.getRole()));
        log.debug("Handled createCustomer.");
        return defaultWriteResponse(flux);
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return defaultReadResponse(this.customerService.findAll());
    }

    public Mono<ServerResponse> getOne(ServerRequest request) {
        return defaultReadResponse((this.customerService.findOne(request.pathVariable("id"))));
    }

    private static Mono<ServerResponse> defaultWriteResponse(Publisher<Customer> customers) {
        return Mono.from(customers).flatMap(customer -> ServerResponse
                        .created(URI.create("/customers/" + customer.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .build()
                );
    }

    private static Mono<ServerResponse> defaultReadResponse(Publisher<Customer> customers) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(customers, CustomerDto.class);
    }
}

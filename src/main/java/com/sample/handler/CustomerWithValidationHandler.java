package com.sample.handler;

import com.sample.db.entity.Customer;
import com.sample.dto.CustomerDto;
import com.sample.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@Component
public class CustomerWithValidationHandler extends AbstractValidationHandler<CustomerDto, Validator> {

    private final CustomerService customerService;

    public CustomerWithValidationHandler(Validator validator, CustomerService customerService) {
        super(CustomerDto.class, validator);
        this.customerService = customerService;
    }

    @Override
    protected Mono<ServerResponse> processBodyFromPost(CustomerDto validBody, ServerRequest originalRequest) {
        Mono<Customer> response = customerService.create(validBody);
        log.debug("Handled createCustomer.");
        return defaultWriteResponse(response);
    }

    @Override
    public Mono<ServerResponse> processGetOne(Long id) {
        return defaultReadResponse((this.customerService.findOne(id)));
    }

    public Mono<ServerResponse> handleGetAll(ServerRequest request) {
        return defaultReadResponse(this.customerService.findAll());
    }

    private static Mono<ServerResponse> defaultWriteResponse(Publisher<Customer> customers) {
        Mono<ServerResponse> result = Mono.from(customers).flatMap(customer -> ServerResponse
                .created(URI.create("/customers/" + customer.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .build()
        );
        return result;
    }

    private static Mono<ServerResponse> defaultReadResponse(Publisher<Customer> customers) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(customers, CustomerDto.class);
    }
}

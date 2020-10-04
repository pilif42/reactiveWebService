package com.sample.handler;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Mono;

public abstract class AbstractValidationHandler<T, U extends Validator> {

    private final Class<T> classToValidate;
    private final U validator;

    protected AbstractValidationHandler(Class<T> clazz, U validator) {
        this.classToValidate = clazz;
        this.validator = validator;
    }

    public final Mono<ServerResponse> createCustomer(final ServerRequest request) {
        return request.bodyToMono(this.classToValidate)
                .flatMap(body -> {
                    Errors errors = new BeanPropertyBindingResult(body, this.classToValidate.getName());
                    this.validator.validate(body, errors);

                    if (errors == null || errors.getAllErrors().isEmpty()) {
                        return processBody(body, request);
                    } else {
                        return onValidationErrors(errors, body, request);
                    }
                });
    }

    protected Mono<ServerResponse> onValidationErrors(Errors errors, T invalidBody, final ServerRequest request) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errors.getAllErrors().toString());
    }

    abstract protected Mono<ServerResponse> processBody(T validBody, final ServerRequest originalRequest);
}

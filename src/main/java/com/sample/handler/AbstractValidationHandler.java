package com.sample.handler;

import com.sample.db.entity.Customer;
import com.sample.dto.CustomerDto;
import com.sample.dto.ErrorDto;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Mono;

import static java.lang.String.format;

public abstract class AbstractValidationHandler<T, U extends Validator> {

    private final Class<T> classToValidate;
    private final U validator;

    protected AbstractValidationHandler(Class<T> clazz, U validator) {
        this.classToValidate = clazz;
        this.validator = validator;
    }

    public final Mono<ServerResponse> handlePost(final ServerRequest request) {
        return request.bodyToMono(this.classToValidate)
                .flatMap(body -> {
                    Errors errors = new BeanPropertyBindingResult(body, this.classToValidate.getName());
                    this.validator.validate(body, errors);

                    if (errors == null || errors.getAllErrors().isEmpty()) {
                        return processBodyFromPost(body, request);
                    } else {
                        return onValidationErrors(errors, body, request);
                    }
                });
    }

    public final Mono<ServerResponse> handleGetOne(final ServerRequest request) {
        String id = request.pathVariable("id");
        try {
            return processGetOne(Long.parseLong(id));
        } catch (NumberFormatException e) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage(format("%s is not a Long.", id));
            return defaultResponse(Mono.just(errorDto));
        }
    }

    protected Mono<ServerResponse> onValidationErrors(Errors errors, T invalidBody, final ServerRequest request) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errors.getAllErrors().toString());
    }

    abstract protected Mono<ServerResponse> processBodyFromPost(T validBody, final ServerRequest originalRequest);

    abstract protected Mono<ServerResponse> processGetOne(Long id);

    private static Mono<ServerResponse> defaultResponse(Publisher<ErrorDto> errors) {
        return ServerResponse
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(errors, ErrorDto.class);
    }
}

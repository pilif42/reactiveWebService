package com.sample.handler;

import com.sample.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

import static com.sample.util.StringUtil.buildErrorMessage;
import static java.lang.String.format;

@Slf4j
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
                        ErrorDto errorDto = new ErrorDto();
                        errorDto.setMessage(buildErrorMessage(errors));
                        return defaultResponse(Mono.just(errorDto));
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

    abstract protected Mono<ServerResponse> processBodyFromPost(T validBody, final ServerRequest originalRequest);

    abstract protected Mono<ServerResponse> processGetOne(Long id);

    private static Mono<ServerResponse> defaultResponse(Publisher<ErrorDto> errors) {
        return ServerResponse
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(errors, ErrorDto.class);
    }
}

package com.sample.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
public class LoggingHandlerFilterFunction implements HandlerFilterFunction<ServerResponse, ServerResponse> {
    @Override
    public Mono<ServerResponse> filter(ServerRequest serverRequest, HandlerFunction<ServerResponse> handlerFunction) {
        log.debug("request is {}", serverRequest);
        log.debug("handlerFunction is {}", handlerFunction);
        return handlerFunction.handle(serverRequest);
    }
}

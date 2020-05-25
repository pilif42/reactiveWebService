package com.sample.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

/**
 * To handle requests and create a response.
 */
@Component
public class GreetingHandler {
    public Mono<ServerResponse> hello(ServerRequest request) {
        /**
         * We return the String "Hello, Spring!" for all requests. In 'real life', we could return a stream of items
         * from a database, a stream of items that were generated by calculations, etc.
         */
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(BodyInserters.fromValue("Hello, Spring!"));
    }
}

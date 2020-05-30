package com.sample.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * To handle requests aimed at Accounts in the database and create a response.
 */
@Component
public class AccountHandler {
    public Mono<ServerResponse> getAll(ServerRequest request) {
        // TODO Use a service to call the db.
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(BodyInserters.fromValue("Hello from Accounts!"));
    }
}

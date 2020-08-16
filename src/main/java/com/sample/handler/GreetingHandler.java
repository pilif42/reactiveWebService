package com.sample.handler;

import com.sample.db.entity.Account;
import com.sample.db.repository.ReactiveAccountRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

import java.util.Arrays;

/**
 * To handle requests and create a response.
 */
@Component
public class GreetingHandler {
    private final ReactiveAccountRepository reactiveAccountRepository;

    public GreetingHandler(ReactiveAccountRepository reactiveAccountRepository) {
        this.reactiveAccountRepository = reactiveAccountRepository;
    }

    public Mono<ServerResponse> hello(ServerRequest request) {
        // Reactive_H2_ITEM - To replicate what Liquibase does when we do startup with H2.
        reactiveAccountRepository.saveAll(Arrays.asList(
                Account.builder().email("johndoe@gmail.com").password("tester123").role("Tester").build(),
                Account.builder().email("chrisrea@gmail.com").password("developer123").role("Developer").build(),
                Account.builder().email("zinedine@gmail.com").password("football123").role("Footballer").build(),
                Account.builder().email("jordan@gmail.com").password("basket123").role("Basketer").build(),
                Account.builder().email("federer@gmail.com").password("tennis123").role("Tennisman").build(),
                Account.builder().email("tiger@gmail.com").password("tennis123").role("Tennisman").build(),
                Account.builder().email("messi@gmail.com").password("foot123").role("Footballer").build()));

        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(BodyInserters.fromValue("Accounts have now been stored. Ready to rumble!"));
    }
}

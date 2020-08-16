package com.sample.handler;

import com.sample.db.entity.Account;
//import com.sample.db.repository.AccountRepository;
import com.sample.db.repository.ReactiveAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * To handle requests aimed at Accounts in the database and create a response.
 */
@Component
public class AccountHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountHandler.class);

    /**
     * Reactive_H2_ITEM
     */
     private final ReactiveAccountRepository accountRepository;
     public AccountHandler(ReactiveAccountRepository accountRepository) {
         this.accountRepository = accountRepository;
     }

    /**
     * H2_ITEM
     */
//    private final AccountRepository accountRepository;
//    public AccountHandler(AccountRepository accountRepository) {
//        this.accountRepository = accountRepository;
//    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        LOGGER.debug("Entering getAll...");

        // H2_ITEM
//        Iterable<Account> accounts = accountRepository.findAll();

        // Reactive_H2_ITEM
        Flux<Account> accountFlux = accountRepository.findAll();
        accountFlux.doOnNext(account -> LOGGER.debug("Account retrieved is {}", account.toString()));
        // TODO Play with this flux to build a proper reactive pipeline. We will need a client (an example one is GreetingWebClient).

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("{\"glossary\":{\"title\":\"example glossary\",\"GlossDiv\":{\"title\":\"S\",\"GlossList\":{\"GlossEntry\":{\"ID\":\"SGML\",\"SortAs\":\"SGML\",\"GlossTerm\":\"Standard Generalized Markup Language\",\"Acronym\":\"SGML\",\"Abbrev\":\"ISO 8879:1986\",\"GlossDef\":{\"para\":\"A meta-markup language, used to create markup languages such as DocBook.\",\"GlossSeeAlso\":[\"GML\",\"XML\"]},\"GlossSee\":\"markup\"}}}}}"));
    }
}

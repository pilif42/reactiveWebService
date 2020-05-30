package com.sample.db.repository;

import com.sample.db.entity.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ReactiveAccountRepository extends ReactiveCrudRepository<Account, String> {
    Flux<Account> findByRole(Mono<String> role);

    @Query("{ 'email': ?0, 'password': ?1}")
    Mono<Account> findByEmailAndPassword(String email, String password);
}

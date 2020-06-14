package com.sample.db.repository;

import com.sample.db.entity.Account;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ReactiveAccountRepository extends ReactiveCrudRepository<Account, String> {
    @Query("SELECT * FROM account WHERE email = :email")
    Mono<Account> findByEmail(String email);

    @Query("{ 'email': ?0, 'password': ?1}")
    Mono<Account> findByEmailAndPassword(String email, String password);
}

/**
 * Reactive_H2_ITEM
 */
package com.sample.db.repository;

import com.sample.db.entity.Account;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ReactiveAccountRepository extends ReactiveCrudRepository<Account, Long> {
    @Query("SELECT * FROM account WHERE email = :email")
    Mono<Account> findByEmail(String email);

    @Query("{ 'email': ?0, 'password': ?1}")
    Mono<Account> findByEmailAndPassword(String email, String password);

    @Query("SELECT * FROM account WHERE role = :role")
    Flux<Account> findByRole(String role);
}

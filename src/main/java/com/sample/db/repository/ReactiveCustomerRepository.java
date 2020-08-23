package com.sample.db.repository;

import com.sample.db.entity.Customer;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveCustomerRepository extends ReactiveCrudRepository<Customer, Long> {
    @Query("SELECT * FROM customer WHERE email = :email")
    Mono<Customer> findByEmail(String email);

    @Query("{ 'email': ?0, 'password': ?1}")
    Mono<Customer> findByEmailAndPassword(String email, String password);

    @Query("SELECT * FROM customer WHERE role = :role")
    Flux<Customer> findByRole(String role);
}

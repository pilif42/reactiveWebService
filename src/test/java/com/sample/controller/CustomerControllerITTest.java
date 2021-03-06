package com.sample.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static java.lang.String.format;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerITTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void getAllCustomersTest() {
        webTestClient
                .get().uri("/annotatedCustomers")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("[{\"id\":1,\"email\":\"A@email.com\",\"password\":\"tester123\",\"role\":\"Tester\"},{\"id\":2,\"email\":\"B@email.com\",\"password\":\"tester123\",\"role\":\"Tester\"},{\"id\":3,\"email\":\"C@email.com\",\"password\":\"tester123\",\"role\":\"Tester\"},{\"id\":4,\"email\":\"D@email.com\",\"password\":\"tester123\",\"role\":\"Tester\"}]");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    public void getCustomerById_expectFound(int customerId) {
        webTestClient
                .get().uri(format("/annotatedCustomers/%d", customerId))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(customerId)
                .jsonPath("$.password").isEqualTo("tester123")
                .jsonPath("$.role").isEqualTo("Tester")
                .jsonPath("$.email").isNotEmpty()
        ;
    }

    @Test
    public void getCustomerById_expectNotFound() {
        webTestClient
                .get().uri("/annotatedCustomers/5")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().jsonPath("$.message").isEqualTo("No customer found for id 5");
    }
}

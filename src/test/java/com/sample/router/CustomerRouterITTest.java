package com.sample.router;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WithMockUser
public class CustomerRouterITTest {

    @LocalServerPort
    private int port;

    @Test
    public void testGetAllCustomers() {
        // GIVEN
        Map customer1 = new LinkedHashMap();
        customer1.put("id", 1);
        customer1.put("email", "A@email.com");
        customer1.put("password", "tester123");
        customer1.put("role", "Tester");

        Map customer2 = new LinkedHashMap();
        customer2.put("id", 2);
        customer2.put("email", "B@email.com");
        customer2.put("password", "tester123");
        customer2.put("role", "Tester");

        Map customer3 = new LinkedHashMap();
        customer3.put("id", 3);
        customer3.put("email", "C@email.com");
        customer3.put("password", "tester123");
        customer3.put("role", "Tester");

        Map customer4 = new LinkedHashMap();
        customer4.put("id", 4);
        customer4.put("email", "D@email.com");
        customer4.put("password", "tester123");
        customer4.put("role", "Tester");

        List<Map> expectedList = Stream.of(customer1, customer2, customer3, customer4).collect(Collectors.toList());

        // WHEN & THEN
        WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build()
                .get()
                .uri("/customers")
                .exchange()
                .expectStatus()
                .isOk().expectBody(List.class)
                .value(List::size, equalTo(4))
                .value(list -> list.containsAll(expectedList), equalTo(true));
    }

    @Test
    public void testGetOneCustomer() {
        Map customer = new LinkedHashMap();
        customer.put("id", 3);
        customer.put("email", "C@email.com");
        customer.put("password", "tester123");
        customer.put("role", "Tester");

        // WHEN & THEN
        WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build()
                .get()
                .uri("/customers/3")
                .exchange()
                .expectStatus()
                .isOk().expectBody(Map.class)
                .value(map -> map.equals(customer), equalTo(true));
    }

    @Test
    public void testGetOneCustomer_expectBadRequestReturnCode() {
        Map result = new LinkedHashMap();
        result.put("message", "a is not a Long.");

        // WHEN & THEN
        WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build()
                .get()
                .uri("/customers/a")
                .exchange()
                .expectStatus()
                .isBadRequest().expectBody(Map.class)
                .value(map -> map.equals(result), equalTo(true));
    }

    // TODO Tests for Post (valid & invalid scenarios)
}

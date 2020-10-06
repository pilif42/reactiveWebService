package com.sample.router;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WithMockUser
public class CustomerRouterITTest {

    @LocalServerPort
    private int port;

    @Test
    public void testGetAllCustomers() {
        WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build()
                .get()
                .uri("/customers")
                .exchange()
                .expectStatus()
                .isOk();

        // TODO .expectBody() = le below
        // TODO [{"id":1,"email":"A@email.com","password":"tester123","role":"Tester"},{"id":2,"email":"B@email.com","password":"tester123","role":"Tester"},{"id":3,"email":"C@email.com","password":"tester123","role":"Tester"},{"id":4,"email":"D@email.com","password":"tester123","role":"Tester"}]
    }
}

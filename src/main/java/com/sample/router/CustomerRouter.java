package com.sample.router;

import com.sample.handler.CustomerWithValidationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class CustomerRouter {
    @Bean
    RouterFunction<ServerResponse> routes(CustomerWithValidationHandler handler) {
        return route(i(POST("/customers")), handler::createCustomer)
                .andRoute(i(GET("/customers")), handler::getAll)
                .andRoute(i(GET("/customers/{id}")), handler::getOne);
    }

    private static RequestPredicate i(RequestPredicate target) {
        return new CaseInsensitiveRequestPredicate(target);
    }
}

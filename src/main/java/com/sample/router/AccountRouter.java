package com.sample.router;

import com.sample.handler.AccountHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class AccountRouter {
    /**
     *  Listens for traffic on the /account path and returns the value provided by our reactive handler class AccountHandler.
     */
    @Bean
    public RouterFunction<ServerResponse> routeGetAll(AccountHandler accountHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/account").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                accountHandler::getAll);
    }
}

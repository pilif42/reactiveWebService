package com.sample.db;

import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Class required to be able to use the H2 console. Otherwise, it is not available as we use Netty. See
 * https://stackoverflow.com/questions/52949088/h2-db-not-accessible-at-localhost8080-h2-console-when-using-webflux
 */
@Component
@Profile("dev")
public class H2 {
    private org.h2.tools.Server webServer;

    private org.h2.tools.Server server;

    @EventListener(org.springframework.context.event.ContextRefreshedEvent.class)
    public void start() throws java.sql.SQLException {
        this.webServer = org.h2.tools.Server.createWebServer("-webPort", "8082", "-tcpAllowOthers").start();
        this.server = org.h2.tools.Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();
    }

    @EventListener(org.springframework.context.event.ContextClosedEvent.class)
    public void stop() {
        this.webServer.stop();
        this.server.stop();
    }
}

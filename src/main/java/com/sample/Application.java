package com.sample;

import com.sample.db.entity.Customer;
import com.sample.db.repository.ReactiveCustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.util.Arrays;

@SpringBootApplication
public class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(ReactiveCustomerRepository repository) {
        return (args) -> {
            // save a few customers
            repository.saveAll(Arrays.asList(
                    Customer.builder().email("johndoe@gmail.com").password("tester123").role("Tester").build(),
                    Customer.builder().email("chrisrea@gmail.com").password("developer123").role("Developer").build(),
                    Customer.builder().email("zinedine@gmail.com").password("football123").role("Footballer").build(),
                    Customer.builder().email("jordan@gmail.com").password("basket123").role("Basketer").build(),
                    Customer.builder().email("federer@gmail.com").password("tennis123").role("Tester").build(),
                    Customer.builder().email("tiger@gmail.com").password("tennis123").role("Tennisman").build(),
                    Customer.builder().email("messi@gmail.com").password("foot123").role("Footballer").build()))
                    .blockLast(Duration.ofSeconds(10));

            // fetch all customers
            LOGGER.info("Customers found with findAll():");
            LOGGER.info("-------------------------------");
            repository.findAll().doOnNext(customer -> {
                LOGGER.info(customer.toString());
            }).blockLast(Duration.ofSeconds(10));

            LOGGER.info("");

            // fetch an individual customer by ID
            repository.findById(1L).doOnNext(customer -> {
                LOGGER.info("Customer found with findById(1L):");
                LOGGER.info("--------------------------------");
                LOGGER.info(customer.toString());
                LOGGER.info("");
            }).block(Duration.ofSeconds(10));


            // fetch customers by role
            LOGGER.info("Customer found with findByRole('Tester'):");
            LOGGER.info("--------------------------------------------");
            repository.findByRole("Tester").doOnNext(customer -> {
                LOGGER.info(customer.toString());
            }).blockLast(Duration.ofSeconds(10));;
            LOGGER.info("");
        };
    }
}

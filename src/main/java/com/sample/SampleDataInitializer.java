package com.sample;

import com.sample.db.entity.Customer;
import com.sample.db.repository.ReactiveCustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class SampleDataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleDataInitializer.class);

    private final ReactiveCustomerRepository reactiveCustomerRepository;

    public SampleDataInitializer(ReactiveCustomerRepository reactiveCustomerRepository) {
        this.reactiveCustomerRepository = reactiveCustomerRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        reactiveCustomerRepository
                .deleteAll()
                .thenMany(
                        Flux.just("A", "B", "C", "D")
                                .map(name -> Customer.builder().email(name + "@email.com").password("tester123").role("Tester").build())
                                .flatMap(reactiveCustomerRepository::save))
                .thenMany(reactiveCustomerRepository.findAll())
                .subscribe(customer -> LOGGER.info("Saving {}", customer.toString()));
    }
}

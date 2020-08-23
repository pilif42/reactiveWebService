package com.sample.event;

import com.sample.db.entity.Customer;
import org.springframework.context.ApplicationEvent;

public class CustomerCreatedEvent extends ApplicationEvent {
    public CustomerCreatedEvent(Customer customer) {
        super(customer);
    }
}

package com.sample.db.entity;

import lombok.Getter;
import lombok.Setter;
//Reactive_H2_ITEM
import org.springframework.data.annotation.Id;

// H2_ITEM: the next 2 lines
//import javax.persistence.Entity;
//import javax.persistence.Id;

@Getter @Setter
//@Entity
public class Account {
    @Id
    private Long id;

    private String email;
    private String password;
    private String role;
}

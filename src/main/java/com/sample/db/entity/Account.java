package com.sample.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter @Setter
public class Account {
    @Id
    private Long id;

    private String email;
    private String password;
    private String role;
}

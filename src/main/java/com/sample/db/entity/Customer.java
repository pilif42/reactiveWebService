package com.sample.db.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;

@EqualsAndHashCode
@Getter @Setter @Builder
public class Customer {
    @Id
    private Long id;

    private String email;
    private String password;
    private String role;

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, email='%s', role='%s']",
                id, email, role);
    }
}

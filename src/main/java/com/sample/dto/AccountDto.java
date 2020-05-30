package com.sample.dto;

import lombok.Data;

@Data
public class AccountDto {
    private Long id;

    private String email;
    private String password;
    private String role;
}

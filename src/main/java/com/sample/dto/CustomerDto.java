package com.sample.dto;

import lombok.Data;

@Data
public class CustomerDto {
    private Long id;
    private String email;
    private String password;
    private String role;
}

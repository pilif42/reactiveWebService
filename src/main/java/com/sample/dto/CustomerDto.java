package com.sample.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CustomerDto {
    private Long id;

    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String role;
}

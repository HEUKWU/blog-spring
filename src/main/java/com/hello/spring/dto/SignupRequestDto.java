package com.hello.spring.dto;

import lombok.Getter;

import javax.validation.constraints.Pattern;

@Getter
public class SignupRequestDto {

    @Pattern(regexp = "[a-z0-9]{4,10}$")
    private String username;
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,15}$")
    private String password;
    private boolean admin = false;
    private String adminToken = "";
}

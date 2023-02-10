package com.hello.spring.dto;

import javax.validation.constraints.Pattern;

public class SignupRequestDto {

    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d).{4,10}$")
    private String username;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,15}$")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

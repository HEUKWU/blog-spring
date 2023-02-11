package com.hello.spring.dto;

import lombok.Getter;

@Getter
public class StatusResponseDto {
    String msg;
    int statusCode;

    public StatusResponseDto(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}

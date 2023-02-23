package com.hello.spring.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseDto<T> {
    private String msg;
    private int statusCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public ResponseDto(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }

    public ResponseDto(String msg, int statusCode, T data) {
        this.msg = msg;
        this.statusCode = statusCode;
        this.data = data;
    }

    public static <T> ResponseDto<T> ok(String msg, T data) {
        return new ResponseDto<>(msg, HttpStatus.OK.value(), data);
    }

    public static <T> ResponseDto<T> fail(String msg, T data) {
        return new ResponseDto<>(msg, HttpStatus.BAD_REQUEST.value(), data);
    }
}

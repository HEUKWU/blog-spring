package com.hello.spring.dto;

import lombok.Getter;

@Getter
public class ReplyRequestDto {
    private String contents;

    public ReplyRequestDto(String contents) {
        this.contents = contents;
    }
}

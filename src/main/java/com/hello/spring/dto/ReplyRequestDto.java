package com.hello.spring.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReplyRequestDto {
    private String contents;

    public ReplyRequestDto(String contents) {
        this.contents = contents;
    }
}

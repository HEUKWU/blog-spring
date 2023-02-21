package com.hello.spring.dto;

import lombok.Getter;

@Getter
public class BlogRequestDto {
    private String title;
    private String contents;

    public BlogRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}

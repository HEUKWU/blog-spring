package com.hello.spring.dto;

import com.hello.spring.entity.Blog;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BlogResponseDto {
    private final String title;
    private final String userName;
    private final String contents;
    private final LocalDateTime createdAt;

    public BlogResponseDto(Blog blog) {
        this.title = blog.getTitle();
        this.userName = blog.getUsername();
        this.contents = blog.getContents();
        this.createdAt = blog.getCreatedAt();
    }
}

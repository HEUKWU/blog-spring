package com.hello.spring.dto;

import com.hello.spring.entity.Blog;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogResponseDto {
    private String title;
    private String userName;
    private String contents;
    private LocalDateTime createdAt;

    public BlogResponseDto(Blog blog) {
        this.title = blog.getTitle();
        this.userName = blog.getUsername();
        this.contents = blog.getContents();
        this.createdAt = blog.getCreatedAt();
    }
}

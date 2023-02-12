package com.hello.spring.dto;

import com.hello.spring.entity.Blog;
import com.hello.spring.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BlogResponseDto {
    private final Long id;
    private final String title;
    private final String userName;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public BlogResponseDto(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.userName = blog.getUser().getUsername();
        this.contents = blog.getContents();
        this.createdAt = blog.getCreatedAt();
        this.modifiedAt = blog.getModifiedAt();
    }

    public BlogResponseDto(Blog blog, User user) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.userName = user.getUsername();
        this.contents = blog.getContents();
        this.createdAt = blog.getCreatedAt();
        this.modifiedAt = blog.getModifiedAt();
    }

}

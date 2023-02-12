package com.hello.spring.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BlogListResponseDto {
    List<BlogResponseDto> blogList = new ArrayList<>();

    public void addBlog(BlogResponseDto dto) {
        blogList.add(dto);
    }
}

package com.hello.spring.controller;

import com.hello.spring.dto.BlogRequestDto;
import com.hello.spring.dto.PasswordDto;
import com.hello.spring.entity.Blog;
import com.hello.spring.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    @PostMapping("/api/blog")
    public Blog createBlog(@RequestBody BlogRequestDto requestDto) {
        return blogService.createBlog(requestDto);
    }

    @GetMapping("/api/blog")
    public List<Blog> getBlog() {
        return blogService.getBlog();
    }

    @PutMapping("/api/blog/{id}")
    public Long updateBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
        return blogService.update(id, requestDto);
    }

    @DeleteMapping("/api/blog/{id}")
    public Long deleteBlog(@PathVariable Long id, @RequestBody PasswordDto dto) {
        return blogService.deleteBlog(id, dto);
    }
}
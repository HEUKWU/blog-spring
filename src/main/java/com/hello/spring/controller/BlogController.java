package com.hello.spring.controller;

import com.hello.spring.dto.BlogRequestDto;
import com.hello.spring.dto.BlogResponseDto;
import com.hello.spring.dto.PasswordDto;
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
    public BlogResponseDto createBlog(@RequestBody BlogRequestDto requestDto) {
        return blogService.createBlog(requestDto);
    }

    @GetMapping("/api/blog")
    public List<BlogResponseDto> getBlog() {
        return blogService.getBlog();
    }

    @GetMapping("/api/blog/{id}")
    public BlogResponseDto getSelectedBlog(@PathVariable Long id) {
        return blogService.getSelectedBlog(id);
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
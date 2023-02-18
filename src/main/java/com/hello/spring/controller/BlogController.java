package com.hello.spring.controller;

import com.hello.spring.dto.BlogListResponseDto;
import com.hello.spring.dto.BlogRequestDto;
import com.hello.spring.dto.BlogResponseDto;
import com.hello.spring.dto.StatusResponseDto;
import com.hello.spring.security.UserDetailsImpl;
import com.hello.spring.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    @PostMapping("/api/blog")
    public BlogResponseDto createBlog(@RequestBody BlogRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return blogService.createBlog(requestDto, userDetails.getUser());
    }

    @GetMapping("/api/blog")
    public BlogListResponseDto getBlog() {
        return blogService.getBlog();
    }

    @GetMapping("/api/blog/{id}")
    public BlogResponseDto getSelectedBlog(@PathVariable Long id) {
        return blogService.getSelectedBlog(id);
    }

    @PutMapping("/api/blog/{id}")
    public BlogResponseDto updateBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return blogService.update(id, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/api/blog/{id}")
    public StatusResponseDto deleteBlog(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return blogService.deleteBlog(id, userDetails.getUser());
    }
}
package com.hello.spring.service;

import com.hello.spring.dto.BlogRequestDto;
import com.hello.spring.dto.BlogResponseDto;
import com.hello.spring.entity.Blog;
import com.hello.spring.entity.User;
import com.hello.spring.jwt.JwtUtil;
import com.hello.spring.repository.BlogRepository;
import com.hello.spring.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public BlogResponseDto createBlog(BlogRequestDto requestDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);

        if (token != null) {
            Claims claims = getClaims(token);

            User user = validateUser(claims);

            Blog blog = blogRepository.saveAndFlush(new Blog(requestDto, user.getId()));
            return new BlogResponseDto(blog);
        } else{
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<BlogResponseDto> getBlog() {
        List<Blog> list = blogRepository.findAllByOrderByModifiedAtDesc();
        return list.stream()
                .map(BlogResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public BlogResponseDto update(Long id, BlogRequestDto requestDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);

        if (token != null) {
            Claims claims = getClaims(token);

            User user = validateUser(claims);

            Blog blog = blogRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new NullPointerException("해당 게시물은 존재하지 않습니다.")
            );
            blog.update(requestDto);
            return new BlogResponseDto(blog);
        } else {
            return null;
        }
    }

    public BlogResponseDto getSelectedBlog(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return new BlogResponseDto(blog);
    }

    public String deleteBlog(Long id, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);

        if (token != null) {

            Claims claims = getClaims(token);

            User user = validateUser(claims);

            blogRepository.deleteById(id);
            return "success";
        } else {
            return null;
        }
    }


    private User validateUser(Claims claims) {
        return userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
    }

    private Claims getClaims(String token) {
        Claims claims;
        if (jwtUtil.validateToken(token)) {
            claims = jwtUtil.getUserInfoFromToken(token);
        } else {
            throw new IllegalStateException("Token Error");
        }
        return claims;
    }
}

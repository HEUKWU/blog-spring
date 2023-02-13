package com.hello.spring.service;

import com.hello.spring.dto.BlogListResponseDto;
import com.hello.spring.dto.BlogRequestDto;
import com.hello.spring.dto.BlogResponseDto;
import com.hello.spring.dto.StatusResponseDto;
import com.hello.spring.entity.Blog;
import com.hello.spring.entity.User;
import com.hello.spring.entity.UserRoleEnum;
import com.hello.spring.jwt.JwtUtil;
import com.hello.spring.repository.BlogRepository;
import com.hello.spring.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

            Blog blog = blogRepository.saveAndFlush(new Blog(requestDto, user));

            return new BlogResponseDto(blog);
        } else{
            throw new IllegalStateException("권한 없음");
        }
    }

    @Transactional(readOnly = true)
    public BlogListResponseDto getBlog() {
        BlogListResponseDto dto = new BlogListResponseDto();

        List<Blog> list = blogRepository.findAllByOrderByModifiedAtDesc();

        for (Blog blog : list) {
            dto.addBlog(new BlogResponseDto(blog));
        }

        return dto;
    }

    @Transactional
    public BlogResponseDto update(Long id, BlogRequestDto requestDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);

        if (token != null) {
            Claims claims = getClaims(token);

            User user = validateUser(claims);

            UserRoleEnum role = user.getRole();
            Blog blog;

            if (role == UserRoleEnum.USER) {
                blog = blogRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                        () -> new NullPointerException("수정 권한이 없습니다.")
                );
            } else {
                blog = blogRepository.findById(id).orElseThrow(NullPointerException::new);
            }
            blog.update(requestDto, user);
            return new BlogResponseDto(blog);
        } else {
            throw new IllegalStateException("권한 없음");
        }
    }

    @Transactional
    public BlogResponseDto getSelectedBlog(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return new BlogResponseDto(blog);
    }

    public StatusResponseDto deleteBlog(Long id, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);

        if (token != null) {

            Claims claims = getClaims(token);

            User user = validateUser(claims);

            UserRoleEnum role = user.getRole();

            if (role == UserRoleEnum.USER) {
                Blog blog = blogRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                        () -> new NullPointerException("삭제 권한이 없습니다.")
                );
                blogRepository.deleteById(blog.getId());
            } else {
                blogRepository.deleteById(id);
            }

            return new StatusResponseDto("게시글 삭제 성공", 200);
        } else {
            throw new IllegalStateException("권한 없음");
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

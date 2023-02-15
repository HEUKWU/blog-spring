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

        User user = jwtUtil.getUser(request, userRepository);

        Blog blog = blogRepository.save(new Blog(requestDto, user));

        return new BlogResponseDto(blog);
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

        User user = jwtUtil.getUser(request, userRepository);

        Blog blog = getBlog(id, user);

        blog.update(requestDto);

        return new BlogResponseDto(blog);
    }

    @Transactional
    public BlogResponseDto getSelectedBlog(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return new BlogResponseDto(blog);
    }

    @Transactional
    public StatusResponseDto deleteBlog(Long id, HttpServletRequest request) {

        User user = jwtUtil.getUser(request, userRepository);

        Blog blog = getBlog(id, user);

        blogRepository.deleteById(blog.getId());

        return new StatusResponseDto("게시글 삭제 성공", 200);
    }

    private Blog getBlog(Long id, User user) {
        return (user.getRole() == UserRoleEnum.USER) ?
                blogRepository.findByIdAndUserId(id, user.getId()).orElseThrow(IllegalArgumentException::new) :
                blogRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}

package com.hello.spring.service;

import com.hello.spring.dto.BlogListResponseDto;
import com.hello.spring.dto.BlogRequestDto;
import com.hello.spring.dto.BlogResponseDto;
import com.hello.spring.dto.StatusResponseDto;
import com.hello.spring.entity.Blog;
import com.hello.spring.entity.User;
import com.hello.spring.entity.UserRoleEnum;
import com.hello.spring.exception.NotFoundContentsException;
import com.hello.spring.exception.NotFoundMemberException;
import com.hello.spring.exception.PermissionException;
import com.hello.spring.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    @Transactional
    public BlogResponseDto createBlog(BlogRequestDto requestDto, User user) {
        Blog blog = blogRepository.save(new Blog(requestDto, user));

        return new BlogResponseDto(blog);
    }

    @Transactional(readOnly = true)
    public BlogListResponseDto getBlog() {
        BlogListResponseDto dto = new BlogListResponseDto();
        List<Blog> blogs = blogRepository.findAllByOrderByModifiedAtDesc();
        for (Blog blog : blogs) {
            dto.addBlog(new BlogResponseDto(blog));
        }

        return dto;
    }

    @Transactional
    public BlogResponseDto update(Long id, BlogRequestDto requestDto, User user) {
        Blog blog = getBlog(id, user);
        blog.update(requestDto);

        return new BlogResponseDto(blog);
    }

    @Transactional
    public BlogResponseDto getSelectedBlog(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(NotFoundMemberException::new);

        return new BlogResponseDto(blog);
    }

    @Transactional
    public StatusResponseDto deleteBlog(Long id, User user) {
        Blog blog = getBlog(id, user);
        blogRepository.deleteById(blog.getId());

        return new StatusResponseDto("게시글 삭제 성공", 200);
    }

    private Blog getBlog(Long id, User user) {
        return (user.getRole() == UserRoleEnum.USER) ?
                blogRepository.findByIdAndUserId(id, user.getId()).orElseThrow(PermissionException::new) :
                blogRepository.findById(id).orElseThrow(NotFoundContentsException::new);
    }
}

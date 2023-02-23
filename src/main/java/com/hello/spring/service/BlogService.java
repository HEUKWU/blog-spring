package com.hello.spring.service;

import com.hello.spring.dto.BlogListResponseDto;
import com.hello.spring.dto.BlogRequestDto;
import com.hello.spring.dto.BlogResponseDto;
import com.hello.spring.dto.ResponseDto;
import com.hello.spring.entity.*;
import com.hello.spring.exception.NotFoundContentsException;
import com.hello.spring.exception.NotFoundMemberException;
import com.hello.spring.exception.PermissionException;
import com.hello.spring.repository.BlogLikeRepository;
import com.hello.spring.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final BlogLikeRepository blogLikeRepository;

    //게시글 작성
    @Transactional
    public ResponseDto<BlogResponseDto> createBlog(BlogRequestDto requestDto, User user) {
        Blog blog = blogRepository.save(new Blog(requestDto, user));
        return ResponseDto.ok("성공", new BlogResponseDto(blog));
    }

    //게시글 조회
    @Transactional(readOnly = true)
    public BlogListResponseDto getBlog() {
        BlogListResponseDto dto = new BlogListResponseDto();
        List<Blog> blogs = blogRepository.findAllByOrderByModifiedAtDesc();
        for (Blog blog : blogs) {
            dto.addBlog(new BlogResponseDto(blog));
        }

        return dto;
    }

    //게시글 수정
    @Transactional
    public BlogResponseDto update(Long id, BlogRequestDto requestDto, User user) {
        Blog blog = getBlog(id, user);
        blog.update(requestDto);

        return new BlogResponseDto(blog);
    }

    //게시글 선택 조회
    @Transactional
    public BlogResponseDto getSelectedBlog(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(NotFoundMemberException::new);

        return new BlogResponseDto(blog);
    }

    //게시글 삭제
    @Transactional
    public ResponseDto<?> deleteBlog(Long id, User user) {
        Blog blog = getBlog(id, user);
        blogRepository.deleteById(blog.getId());
        return ResponseDto.ok("게시글 삭제 성공", null);
    }

    //게시글 좋아요
    @Transactional
    public ResponseDto<?> like(Long id, User user) {
        Blog blog = blogRepository.findById(id).orElseThrow(NotFoundContentsException::new);

        return getStatusResponseDto(user, blog);
    }

    //메서드 추출
    private Blog getBlog(Long id, User user) {
        return (user.getRole() == UserRoleEnum.USER) ?
                blogRepository.findByIdAndUserId(id, user.getId()).orElseThrow(PermissionException::new) :
                blogRepository.findById(id).orElseThrow(NotFoundContentsException::new);
    }

    private ResponseDto<?> getStatusResponseDto(User user, Blog blog) {
        Optional<BlogLike> found = blogLikeRepository.findByUserId(user.getId());
        if (found.isPresent()) {
            blogLikeRepository.deleteBlogLikeByUserId(user.getId());
            return ResponseDto.ok("좋아요 취소", null);
        }
        blogLikeRepository.save(new BlogLike(blog, user));
        return ResponseDto.ok("좋아요 성공", null);
    }
}

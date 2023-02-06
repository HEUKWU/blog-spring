package com.hello.spring.service;

import com.hello.spring.dto.BlogRequestDto;
import com.hello.spring.dto.PasswordDto;
import com.hello.spring.entity.Blog;
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
    public Blog createBlog(BlogRequestDto requestDto) {

        Blog blog = new Blog(requestDto);
        blogRepository.save(blog);
        return blog;
    }

    @Transactional(readOnly = true)
    public List<Blog> getBlog() {
        return blogRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional
    public Long update(Long id, BlogRequestDto requestDto) {
        Blog blog = findByValidateId(id);
        validatePassword(requestDto.getPassword(), blog.getPassword());
        blog.update(requestDto);
        return blog.getId();
    }

    public Blog getSelectedBlog(Long id) {
        return findByValidateId(id);
    }

    public Long deleteBlog(Long id, PasswordDto dto) {
        Blog blog = findByValidateId(id);
        validatePassword(dto.getPassword(), blog.getPassword());
        blogRepository.deleteById(id);
        return blog.getId();
    }

    private Blog findByValidateId(Long id) {
        return blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
    }

    private static void validatePassword(String requestPassword, String blogPassword) {
        if (!blogPassword.equals(requestPassword)) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
    }
}

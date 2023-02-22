package com.hello.spring.entity;

import com.hello.spring.dto.BlogRequestDto;
import com.hello.spring.repository.BlogRepository;
import com.hello.spring.service.BlogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BlogTest {

    @Mock
    BlogRepository blogRepository;

    @InjectMocks
    BlogService blogService;

    @Mock
    User user;

    private String title;
    private String contents;

    @BeforeEach
    void beforeEach() {
        user = new User("abc", "abc", UserRoleEnum.USER);
        title = "title1";
        contents = "content1";
    }

    @Test
    @DisplayName("게시글 작성")
    void createBlog() {

        //given
        BlogRequestDto dto = new BlogRequestDto(title, contents);

        //when
        Blog blog = new Blog(dto, user);

        //then
        assertEquals(user, blog.getUser());
        assertEquals(title, blog.getTitle());
        assertEquals(contents, blog.getContents());
        assertEquals(0, blog.getBlogLikes().size());
    }

//    @Test
//    @DisplayName("게시글 좋아요")
//    void likeBlog() {
//
//        //given
//        BlogRequestDto dto = new BlogRequestDto(title, contents);
//
//        //when
//        Blog blog = new Blog(dto, user);
//        blog = blogRepository.save(blog);
//        blogService.like(1L, user);
//        //then
//        assertEquals(1, blog.getBlogLikes().size());
//    }
}
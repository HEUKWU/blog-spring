package com.hello.spring.dto;

import com.hello.spring.entity.Blog;
import com.hello.spring.entity.Reply;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class BlogResponseDto {
    private Long id;
    private String title;
    private String userName;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<ReplyResponseDto> commentList = new ArrayList<>();

    public BlogResponseDto(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.userName = blog.getUser().getUsername();
        this.contents = blog.getContents();
        this.createdAt = blog.getCreatedAt();
        this.modifiedAt = blog.getModifiedAt();

        List<ReplyResponseDto> list = new ArrayList<>();
        for (Reply reply : blog.getReply()) {
            list.add(new ReplyResponseDto(reply));
        }
        this.commentList = list;
    }

//    public BlogResponseDto(Blog blog, User user) {
//        this.id = blog.getId();
//        this.title = blog.getTitle();
//        this.userName = user.getUsername();
//        this.contents = blog.getContents();
//        this.createdAt = blog.getCreatedAt();
//        this.modifiedAt = blog.getModifiedAt();
//        this.commentList = blog.getReply();
//    }
}

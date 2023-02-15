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
    }

    public List<ReplyResponseDto> getReplies(List<Reply> replies) {
        for (Reply reply : replies) {
            commentList.add(new ReplyResponseDto(reply));
        }
        return commentList;
    }
}

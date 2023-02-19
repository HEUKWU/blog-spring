package com.hello.spring.dto;

import com.hello.spring.entity.Blog;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class BlogResponseDto {
    private Long id;
    private String title;
    private String userName;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Integer likeCount;
    private List<ReplyResponseDto> commentList = new ArrayList<>();

    public BlogResponseDto(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.userName = blog.getUser().getUsername();
        this.contents = blog.getContents();
        this.createdAt = blog.getCreatedAt();
        this.modifiedAt = blog.getModifiedAt();
        this.likeCount = blog.getBlogLikes().size();
        this.commentList = blog.getReplies().stream().sorted((a, b) ->
                        b.getModifiedAt().compareTo(a.getModifiedAt()))
                        .map(ReplyResponseDto::new)
                        .collect(Collectors.toList());
    }
}

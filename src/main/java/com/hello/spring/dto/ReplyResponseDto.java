package com.hello.spring.dto;

import com.hello.spring.entity.Reply;
import com.hello.spring.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReplyResponseDto {

    private Long id;
    private String contents;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ReplyResponseDto(Reply reply, User user) {
        this.id = reply.getId();
        this.contents = reply.getContents();
        this.username = user.getUsername();
        this.createdAt = reply.getCreatedAt();
        this.modifiedAt = reply.getModifiedAt();
    }

    public ReplyResponseDto(Reply reply) {
        this.id = reply.getId();
        this.contents = reply.getContents();
        this.username = reply.getUser().getUsername();
        this.createdAt = reply.getCreatedAt();
        this.modifiedAt = reply.getModifiedAt();
    }
}

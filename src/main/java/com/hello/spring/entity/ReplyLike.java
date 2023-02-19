package com.hello.spring.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class ReplyLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "replyId", nullable = false)
    private Reply reply;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    public ReplyLike(Reply reply, User user) {
        this.reply = reply;
        this.user = user;
    }
}

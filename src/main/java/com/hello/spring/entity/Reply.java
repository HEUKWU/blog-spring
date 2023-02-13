package com.hello.spring.entity;

import com.hello.spring.dto.ReplyRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Reply extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "blogId")
    private Blog blog;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public Reply(ReplyRequestDto dto, Blog blog, User user) {
        this.contents = dto.getContents();
        this.blog = blog;
        this.user = user;
    }

    public void update(ReplyRequestDto dto, User user) {
        this.contents = dto.getContents();
        this.user = user;
    }
}

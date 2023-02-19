package com.hello.spring.entity;

import com.hello.spring.dto.ReplyRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Reply extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blogId", nullable = false)
    private Blog blog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @OneToMany(mappedBy = "reply")
    private List<ReplyLike> replyLikes = new ArrayList<>();

    public Reply(ReplyRequestDto dto, Blog blog, User user) {
        this.contents = dto.getContents();
        this.blog = blog;
        this.user = user;
    }

    public void update(ReplyRequestDto dto) {
        this.contents = dto.getContents();
    }
}

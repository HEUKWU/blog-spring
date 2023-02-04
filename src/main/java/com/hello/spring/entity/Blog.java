package com.hello.spring.entity;

import com.hello.spring.dto.BlogRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Blog extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String password;

    public Blog(BlogRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.password = requestDto.getPassword();

    }

    public void update(BlogRequestDto requestDto) {
        if (requestDto.getPassword().equals(this.password)) {
            this.username = requestDto.getUsername();
            this.title = requestDto.getTitle();
            this.contents = requestDto.getContents();
        } else {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
    }
}
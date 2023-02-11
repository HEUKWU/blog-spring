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
    private Long userId;

    public Blog(BlogRequestDto requestDto, Long userId, String username) {

        this.username = username;
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.userId = userId;
    }

    public void update(BlogRequestDto requestDto, String username) {
        this.username = username;
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }
}
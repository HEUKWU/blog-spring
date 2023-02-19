package com.hello.spring.repository;

import com.hello.spring.entity.ReplyLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReplyLikeRepository extends JpaRepository<ReplyLike, Long> {
    Optional<ReplyLike> findByUserId(Long userId);
    void deleteReplyLikeByUserId(Long userId);
}

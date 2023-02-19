package com.hello.spring.repository;

import com.hello.spring.entity.BlogLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogLikeRepository extends JpaRepository<BlogLike, Long> {
    Optional<BlogLike> findByUserId(Long userId);
    void deleteBlogLikeByUserId(Long userId);
}

package com.hello.spring.repository;

import com.hello.spring.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    List<Blog> findAllByOrderByModifiedAtDesc();
    Optional<Blog> findByIdAndUserId(Long id, Long userId);

}

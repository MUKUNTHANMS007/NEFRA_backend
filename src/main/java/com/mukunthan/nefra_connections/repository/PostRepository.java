package com.mukunthan.nefra_connections.repository;

import com.mukunthan.nefra_connections.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();

    // THE FIX: New method to fetch posts for a specific user profile
    List<Post> findByUserIdOrderByCreatedAtDesc(Long userId);
}
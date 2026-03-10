package com.mukunthan.nefra_connections.repository;

import com.mukunthan.nefra_connections.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();

    // THE FIX: New method to fetch posts for a specific user profile
    List<Post> findByUserIdOrderByCreatedAtDesc(Long userId);
    // Add this to PostRepository.java
    @Query("SELECT p FROM Post p WHERE p.user.id = :userId OR p.user.id IN (" +
            "SELECT c.entrepreneur.id FROM Connection c WHERE c.investor.id = :userId AND c.status = 'ACCEPTED') " +
            "OR p.user.id IN (" +
            "SELECT c.investor.id FROM Connection c WHERE c.entrepreneur.id = :userId AND c.status = 'ACCEPTED') " +
            "ORDER BY p.createdAt DESC")
    List<Post> findPostsForUserFeed(@org.springframework.data.repository.query.Param("userId") Long userId);
}
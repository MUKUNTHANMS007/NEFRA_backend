package com.mukunthan.nefra_connections.repository;

import com.mukunthan.nefra_connections.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLike.PostLikeId> {
    Optional<PostLike> findByPostIdAndUserId(Long postId, Long userId);
}
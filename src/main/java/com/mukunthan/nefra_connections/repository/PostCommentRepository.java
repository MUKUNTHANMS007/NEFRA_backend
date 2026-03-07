package com.mukunthan.nefra_connections.repository;

import com.mukunthan.nefra_connections.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
}
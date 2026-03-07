package com.mukunthan.nefra_connections.dto;

import java.time.LocalDateTime;

public record PostResponseDTO(
        Long postId,
        Long authorId,
        String authorName,
        String authorProfileImageUrl,
        String title,
        String description,
        String imageUrl,
        LocalDateTime createdAt,
        Integer likeCount,
        Integer commentCount
) {}
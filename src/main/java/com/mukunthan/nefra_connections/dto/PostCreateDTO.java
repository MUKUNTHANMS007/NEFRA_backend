package com.mukunthan.nefra_connections.dto;

public record PostCreateDTO(
        Long userId,
        String title,
        String description,
        String imageUrl
) {}
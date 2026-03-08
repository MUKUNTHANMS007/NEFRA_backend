package com.mukunthan.nefra_connections.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PostCreateDTO(
        Long userId,
        String title,

        // This is the magic line that fixes your Hibernate crash
        @JsonProperty("content")
        String description,

        String imageUrl
) {}
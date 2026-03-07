package com.mukunthan.nefra_connections.dto;
import com.mukunthan.nefra_connections.enums.UserRole;

public record AuthResponseDTO(
        String token,
        Long userId,
        UserRole role
) {}
package com.mukunthan.nefra_connections.dto;

import com.mukunthan.nefra_connections.enums.DomainType;
import com.mukunthan.nefra_connections.enums.UserRole;

public record UserDTO(
        Long id,
        String username,
        String fullName,
        UserRole role,
        DomainType domainType,
        String location,
        String profileImageUrl,
        String description
) {}
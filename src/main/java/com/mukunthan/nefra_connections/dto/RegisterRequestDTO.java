package com.mukunthan.nefra_connections.dto;
import com.mukunthan.nefra_connections.enums.DomainType;
import com.mukunthan.nefra_connections.enums.UserRole;

public record RegisterRequestDTO(
        String username,
        String email,
        String password,
        String fullName,
        UserRole role,
        DomainType domainType
) {}
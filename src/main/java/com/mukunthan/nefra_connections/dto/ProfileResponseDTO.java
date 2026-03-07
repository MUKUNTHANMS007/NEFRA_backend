package com.mukunthan.nefra_connections.dto;

import com.mukunthan.nefra_connections.enums.DomainType;
import com.mukunthan.nefra_connections.enums.UserRole;
import java.util.List;

public record ProfileResponseDTO(
        Long userId,
        String username,
        String fullName,
        String email,
        UserRole role,
        DomainType domainType,
        String location,
        String description,
        String profileImageUrl,

        // Entrepreneur Specific
        List<String> skills,
        List<ExperienceDTO> experiences,

        // Investor Specific
        List<PortfolioDTO> portfolios
) {}
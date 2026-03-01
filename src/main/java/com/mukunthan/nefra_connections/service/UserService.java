package com.mukunthan.nefra_connections.service;

import com.mukunthan.nefra_connections.dto.UserDto;
import com.mukunthan.nefra_connections.entity.User;
import com.mukunthan.nefra_connections.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    /**
     * Phase 1: Identity & Search Logic
     * Refactored to use Repository methods directly (Clears Repository Warnings)
     */
    public List<UserDto> searchUsers(String query, String role, String industry) {

        // 1. If searching by Role specifically
        if (role != null && !role.isEmpty()) {
            try {
                User.Role roleEnum = User.Role.valueOf(role.toUpperCase());
                return repository.findByRole(roleEnum).stream()
                        .map(this::mapToDto)
                        .collect(Collectors.toList());
            } catch (IllegalArgumentException e) {
                return Collections.emptyList();
            }
        }

        // 2. If searching by Industry specifically
        if (industry != null && !industry.isEmpty()) {
            return repository.findByIndustryIgnoreCase(industry).stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());
        }

        // 3. Fallback: Search by Name or return all
        if (query != null && !query.isEmpty()) {
            return repository.findByNameContainingIgnoreCase(query).stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());
        }

        return repository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Converts the User Entity (Database) to UserDto (Frontend)
     */
    public UserDto mapToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole()); // This matches the User.Role enum in UserDto
        dto.setCompany(user.getCompany());
        dto.setIndustry(user.getIndustry());
        dto.setImageUrl(user.getImageUrl());
        dto.setVerified(user.isVerified());
        return dto;
    }
}
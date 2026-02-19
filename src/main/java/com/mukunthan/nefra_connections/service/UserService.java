package com.mukunthan.nefra_connections.service;

import com.mukunthan.nefra_connections.dto.UserDto;
import com.mukunthan.nefra_connections.entity.User;
import com.mukunthan.nefra_connections.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public List<UserDto> getFeaturedUsers() {
        return repository.findAll().stream()
                .limit(4) // Gets the top 4 (Mukunthan, Siddiq, Jensen, Elon)
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private UserDto mapToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setRole(user.getRole());
        dto.setCompany(user.getCompany());
        dto.setIndustry(user.getIndustry());
        dto.setImageUrl(user.getImageUrl());
        dto.setVerified(user.isVerified());
        return dto;
    }
}
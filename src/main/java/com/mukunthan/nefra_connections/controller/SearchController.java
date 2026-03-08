package com.mukunthan.nefra_connections.controller;

import com.mukunthan.nefra_connections.dto.UserDTO;
import com.mukunthan.nefra_connections.enums.DomainType;
import com.mukunthan.nefra_connections.enums.UserRole;
import com.mukunthan.nefra_connections.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class SearchController {

    private final UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> searchUsers(
            @RequestParam(required = false) UserRole role, // THE FIX: Make role optional
            @RequestParam(required = false) DomainType domain) {

        List<com.mukunthan.nefra_connections.entity.User> users;

        // THE FIX: Smart routing based on what the frontend actually asks for
        if (role != null && domain != null) {
            users = userRepository.findByRoleAndDomainType(role, domain);
        } else if (role != null) {
            users = userRepository.findByRole(role);
        } else {
            // If the frontend asks for everyone, give them everyone
            users = userRepository.findAll();
        }

        List<UserDTO> response = users.stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getFullName(),
                        user.getRole(),
                        user.getDomainType(),
                        user.getLocation(),
                        user.getProfileImageUrl(),
                        user.getDescription()
                )).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}

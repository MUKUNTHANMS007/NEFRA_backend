package com.mukunthan.nefra_connections.controller;

import com.mukunthan.nefra_connections.dto.UserDto;
import com.mukunthan.nefra_connections.entity.User;
import com.mukunthan.nefra_connections.repository.UserRepository;
import com.mukunthan.nefra_connections.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    /**
     * FIX: Added this endpoint to resolve the Whitelabel Error on the Home Page.
     * Returns verified users (Investors and Entrepreneurs) for the featured section.
     */
    @GetMapping("/featured")
    public List<UserDto> getFeatured() {
        return userRepository.findAll().stream()
                .filter(User::isVerified) // Only show verified users as featured
                .map(userService::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Handles login requests from the SignInPage.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        return userRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password))
                .map(user -> ResponseEntity.ok((Object) userService.mapToDto(user)))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password"));
    }

    /**
     * Search endpoint used by the Find Investors page.
     */
    @GetMapping("/search")
    public List<UserDto> search(@RequestParam(required = false) String query,
                                @RequestParam(required = false) String role,
                                @RequestParam(required = false) String industry) {
        return userService.searchUsers(query, role, industry);
    }
}
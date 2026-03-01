package com.mukunthan.nefra_connections.controller;

import com.mukunthan.nefra_connections.entity.User;
import com.mukunthan.nefra_connections.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // Fixes the CORS block from React
public class AuthController {

    private final UserRepository userRepository;

    /**
     * Handles User Registration.
     * Checks if email exists before saving.
     */
    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody User user) {
        // findByEmail returns Optional<User>, so .isPresent() is valid here
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    /**
     * Handles User Login.
     * Uses Optional functional flow to verify credentials.
     */
    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        return userRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password))
                .map(user -> ResponseEntity.ok(user)) // If found and password matches
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()); // If not found or wrong pass
    }
}
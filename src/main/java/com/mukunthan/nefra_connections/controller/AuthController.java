package com.mukunthan.nefra_connections.controller;

import com.mukunthan.nefra_connections.dto.AuthRequestDTO;
import com.mukunthan.nefra_connections.dto.AuthResponseDTO;
import com.mukunthan.nefra_connections.dto.RegisterRequestDTO;
import com.mukunthan.nefra_connections.service.AuthService;
import com.mukunthan.nefra_connections.repository.UserRepository;
import com.mukunthan.nefra_connections.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> authenticate(@RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/me")
    public ResponseEntity<AuthResponseDTO> getCurrentUser(@RequestParam Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(new AuthResponseDTO(null, user.getId(), user.getRole()));
    }
}
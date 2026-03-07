package com.mukunthan.nefra_connections.service;

import com.mukunthan.nefra_connections.dto.*;
import com.mukunthan.nefra_connections.entity.User;
import com.mukunthan.nefra_connections.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    /**
     * Registers a new user with plain text password storage.
     */
    public AuthResponseDTO register(RegisterRequestDTO request) {
        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPasswordHash(request.password()); // No encoding, just direct save
        user.setFullName(request.fullName());
        user.setRole(request.role());
        user.setDomainType(request.domainType());

        User savedUser = userRepository.save(user);

        // Token is null because JWT is removed
        return new AuthResponseDTO(null, savedUser.getId(), savedUser.getRole());
    }

    /**
     * Authenticates a user using plain text comparison.
     */
    public AuthResponseDTO authenticate(AuthRequestDTO request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        // Honest comparison: no sugar-coating, just direct string match
        if (!user.getPasswordHash().equals(request.password())) {
            // We use ResponseStatusException to send a 401 instead of a 500 crash
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        return new AuthResponseDTO(null, user.getId(), user.getRole());
    }
}
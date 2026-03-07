package com.mukunthan.nefra_connections.controller;

import com.mukunthan.nefra_connections.dto.ProfileResponseDTO;
import com.mukunthan.nefra_connections.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponseDTO> getProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(profileService.getUserProfile(userId));
    }
    @PutMapping("/{userId}")
    public org.springframework.http.ResponseEntity<com.mukunthan.nefra_connections.dto.ProfileResponseDTO> updateProfile(
            @PathVariable Long userId,
            @RequestBody com.mukunthan.nefra_connections.dto.ProfileResponseDTO updateData) {
        return org.springframework.http.ResponseEntity.ok(profileService.updateProfile(userId, updateData));
    }
}
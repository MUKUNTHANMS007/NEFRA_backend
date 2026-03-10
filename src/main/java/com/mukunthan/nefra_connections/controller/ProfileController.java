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

    // Notice the new request parameter: ?viewerId=X
    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponseDTO> getProfile(
            @PathVariable Long userId,
            @RequestParam(required = false) Long viewerId) {
        return ResponseEntity.ok(profileService.getUserProfile(userId, viewerId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ProfileResponseDTO> updateProfile(
            @PathVariable Long userId,
            @RequestBody ProfileResponseDTO updateData) {
        return ResponseEntity.ok(profileService.updateProfile(userId, updateData));
    }
}
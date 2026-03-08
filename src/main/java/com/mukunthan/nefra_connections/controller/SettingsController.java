package com.mukunthan.nefra_connections.controller;

import com.mukunthan.nefra_connections.dto.SettingsUpdateDTO;
import com.mukunthan.nefra_connections.service.SettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/settings")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class SettingsController {

    private final SettingsService settingsService;

    @GetMapping("/{userId}")
    public ResponseEntity<SettingsUpdateDTO> getSettings(@PathVariable Long userId) {
        return ResponseEntity.ok(settingsService.getSettings(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateSettings(
            @PathVariable Long userId,
            @RequestBody SettingsUpdateDTO request) {
        settingsService.updateSettings(userId, request);
        return ResponseEntity.ok("Settings updated successfully");
    }
}
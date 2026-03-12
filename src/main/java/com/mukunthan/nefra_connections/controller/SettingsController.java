package com.mukunthan.nefra_connections.controller;

import com.mukunthan.nefra_connections.dto.SettingsUpdateDTO;
import com.mukunthan.nefra_connections.service.SettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
// THE FIX: This path must exactly match what React is calling in your screenshot
@RequestMapping("/api/v1/settings")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Wildcard allows your frontend to connect regardless of the port
public class SettingsController {

    private final SettingsService settingsService;

    @GetMapping("/{userId}")
    public ResponseEntity<SettingsUpdateDTO> getSettings(@PathVariable Long userId) {
        return ResponseEntity.ok(settingsService.getSettings(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Map<String, String>> updateSettings(
            @PathVariable Long userId,
            @RequestBody SettingsUpdateDTO request) {

        settingsService.updateSettings(userId, request);
        return ResponseEntity.ok(Map.of("message", "System configuration synced successfully"));
    }
}
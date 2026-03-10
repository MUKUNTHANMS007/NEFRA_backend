package com.mukunthan.nefra_connections.controller;

import com.mukunthan.nefra_connections.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/connections")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ConnectionController {

    private final ConnectionService connectionService;

    @PostMapping("/request")
    public ResponseEntity<String> requestConnection(
            @RequestParam Long senderId,
            @RequestParam Long recipientId) {
        try {
            String result = connectionService.sendConnectionRequest(senderId, recipientId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/respond")
    public ResponseEntity<String> respondToConnection(
            @RequestParam Long senderId,
            @RequestParam Long recipientId,
            @RequestParam String action) {
        try {
            String result = connectionService.respondToRequest(senderId, recipientId, action);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
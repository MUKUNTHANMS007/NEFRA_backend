package com.mukunthan.nefra_connections.controller;

import com.mukunthan.nefra_connections.dto.ConnectionRequestDTO;
import com.mukunthan.nefra_connections.dto.ConnectionResponseDTO;
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
    public ResponseEntity<ConnectionResponseDTO> requestConnection(@RequestBody ConnectionRequestDTO request) {
        ConnectionResponseDTO response = connectionService.initiateConnection(request);
        return ResponseEntity.ok(response);
    }
}
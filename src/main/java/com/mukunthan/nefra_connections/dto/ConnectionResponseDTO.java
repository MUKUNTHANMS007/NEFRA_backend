package com.mukunthan.nefra_connections.dto;

public record ConnectionResponseDTO(
        Long investorId,
        String investorName,
        Long entrepreneurId,
        String entrepreneurName,
        String status
) {}
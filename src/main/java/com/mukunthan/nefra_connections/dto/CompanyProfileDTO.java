package com.nefra.dto;

import java.util.Map;
import java.util.List;

public record CompanyProfileDTO(
        // 1. Core Identity
        Long id,
        String name,
        String industry,
        String location,
        String website,
        String teamSize,

        // 2. The Bio & Solution
        String description,
        String solution,
        String tagline,

        // 3. Performance Metrics (For the Dashboard)
        Double totalHoursLogged,
        Map<String, Double> hourBreakdown, // e.g., {"product": 65.0, "growth": 30.0}

        // 4. Intelligence & History
        String aiBriefing, // Pre-cached AI analysis if you want to store it
        List<MilestoneDTO> milestones
) {
    // Inner DTO for clean milestone mapping
    public record MilestoneDTO(String year, String event) {}
}
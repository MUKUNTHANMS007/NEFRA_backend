package com.mukunthan.nefra_connections.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class UserProfileDTO {
    private Long id;
    private String fullName;
    private String username;

    // --- NEW: THE MISSING INTEL ---
    private String headline;
    private String domainType;
    private String location;
    private String linkedinUrl;
    private String githubUrl;
    // ------------------------------

    private String role;
    private String industry;
    private String description;
    private Integer foundedYear;

    // UPGRADED: Changed to BigDecimal to match your Entity and prevent rounding errors
    private BigDecimal totalAssets;

    private Boolean isVerified;

    // Dynamic Calculated Fields
    private Long connectionCount; // "Following"
    private Long postCount;       // "Followers" (Repurposed for your UI)
    private String connectionStatus; // "NONE", "PENDING", "ACCEPTED"
}
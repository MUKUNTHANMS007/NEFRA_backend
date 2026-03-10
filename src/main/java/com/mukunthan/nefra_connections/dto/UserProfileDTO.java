package com.mukunthan.nefra_connections.dto;

import lombok.Data;

@Data
public class UserProfileDTO {
    private Long id;
    private String fullName;
    private String username;
    private String role;
    private String industry;
    private String description;
    private Integer foundedYear;
    private Double totalAssets;
    private Boolean isVerified;

    // Dynamic Calculated Fields
    private Long connectionCount; // "Following"
    private Long postCount;       // "Followers" (Repurposed for your UI)
    private String connectionStatus; // "NONE", "PENDING", "ACCEPTED"
}
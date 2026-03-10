package com.mukunthan.nefra_connections.dto;

import com.mukunthan.nefra_connections.enums.DomainType;
import com.mukunthan.nefra_connections.enums.UserRole;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProfileResponseDTO {
    private Long id;
    private String username;
    private String fullName;
    private UserRole role;
    private DomainType domainType;
    private String location;
    private String profileImageUrl;
    private String description;

    private String headline;
    private String industry;
    private Integer foundedYear;
    private BigDecimal totalAssets;
    private Boolean isVerified;

    // Dynamic Networking Stats (Mapping to your UI "Followers/Following")
    private Long connectionCount; // Think of this as "Following"
    private Long postCount;       // Think of this as "Followers"

    // CRITICAL: Relationship between the viewer and this profile
    private String connectionStatus; // e.g., "NONE", "PENDING", "ACCEPTED", "SELF"
}
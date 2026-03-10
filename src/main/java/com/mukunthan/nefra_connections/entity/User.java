package com.mukunthan.nefra_connections.entity;

import com.mukunthan.nefra_connections.enums.UserRole;
import com.mukunthan.nefra_connections.enums.DomainType;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    private String fullName;

    // NEW: The professional headline (e.g., "SaaS Founder | Tech Enthusiast")
    private String headline;

    private String location;
    private String profileImageUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", columnDefinition = "VARCHAR(255)")
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private DomainType domainType;

    // NEW FIELDS TO MATCH YOUR RECENT ALTER TABLE COMMANDS
    private String industry;

    @Builder.Default
    private Integer foundedYear = 2026;

    @Builder.Default
    private Integer connectionCount = 0;

    @Builder.Default
    private Integer postCount = 0;

    @Column(name = "is_verified")
    private Boolean isVerified = false;

    private String linkedinUrl;
    private String githubUrl;

    @Column(precision = 15, scale = 2)
    private java.math.BigDecimal totalAssets;
}
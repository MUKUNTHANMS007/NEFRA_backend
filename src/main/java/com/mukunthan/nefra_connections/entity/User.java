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

    // Hibernate maps camelCase 'passwordHash' to snake_case 'password_hash' in MySQL
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    private String fullName;
    private String location;
    private String profileImageUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private DomainType domainType;
}
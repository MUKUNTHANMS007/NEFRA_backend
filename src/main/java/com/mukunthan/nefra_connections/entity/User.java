package com.mukunthan.nefra_connections.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email; // FIX: Add this line to match the Repository method
    private String role;
    private String company;
    private String industry;
    private String imageUrl;
    private boolean verified;
}
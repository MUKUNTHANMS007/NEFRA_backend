package com.mukunthan.nefra_connections.entity;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "companies")
@Data
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // This IS your user_id. It links the company to the User who owns it.
    @OneToOne
    @JoinColumn(name = "entrepreneur_id", referencedColumnName = "id", nullable = false)
    private User user;

    private String name;
    private String tagline;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String domainType;
    private String location;
    private String websiteUrl;
    private String fundingStage;
    private Integer teamSize;

    // These should stay for your dashboard metrics
    private Double totalHoursLogged = 0.0;
    private Double productHours = 0.0;
    private Double growthHours = 0.0;
    private Double opsHours = 0.0;
}
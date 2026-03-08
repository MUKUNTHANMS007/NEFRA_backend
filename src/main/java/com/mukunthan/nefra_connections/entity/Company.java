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

    // The critical One-to-One link back to your User table
    @OneToOne
    @JoinColumn(name = "entrepreneur_id", referencedColumnName = "id", nullable = false)
    private User entrepreneur;

    private String name;
    private String tagline;
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "domain_type")
    private String domainType;

    private String location;
    @Column(name = "website_url")
    private String websiteUrl;
    @Column(name = "logo_url")
    private String logoUrl;
    @Column(name = "funding_stage")
    private String fundingStage;
}
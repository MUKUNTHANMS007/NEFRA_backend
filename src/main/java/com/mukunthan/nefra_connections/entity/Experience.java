package com.mukunthan.nefra_connections.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "experiences")
@Getter @Setter @NoArgsConstructor
public class Experience {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "start_year", nullable = false)
    private Integer startYear;

    @Column(name = "end_year")
    private Integer endYear; // Null = Present

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "company_role", nullable = false)
    private String companyRole;
}
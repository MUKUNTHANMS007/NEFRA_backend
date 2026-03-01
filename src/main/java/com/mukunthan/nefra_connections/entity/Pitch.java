package com.mukunthan.nefra_connections.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pitches")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pitch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String industry;

    @Column(name = "funding_goal")
    private Double fundingGoal;

    private String status;

    @ManyToOne
    @JoinColumn(name = "entrepreneur_id", nullable = false)
    private User entrepreneur;
}
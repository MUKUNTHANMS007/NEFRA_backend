package com.mukunthan.nefra_connections.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "success_stories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessStory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String metric;
    private String outcome;
    private String author;
    private String company;
    private String imageUrl;

    private boolean featured = true; // This is the key for getFeaturedStories()
}
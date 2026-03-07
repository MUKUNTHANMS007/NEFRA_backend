package com.mukunthan.nefra_connections.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Getter @Setter @NoArgsConstructor
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // The N+1 Killers: These compute at the database level during the initial fetch.
    @Formula("(SELECT COUNT(*) FROM post_likes pl WHERE pl.post_id = id)")
    private Integer likeCount;

    @Formula("(SELECT COUNT(*) FROM post_comments pc WHERE pc.post_id = id)")
    private Integer commentCount;
}
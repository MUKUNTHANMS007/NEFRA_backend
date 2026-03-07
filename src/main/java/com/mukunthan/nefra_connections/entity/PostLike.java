package com.mukunthan.nefra_connections.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "post_likes")
@Getter @Setter @NoArgsConstructor
public class PostLike {

    @EmbeddedId
    private PostLikeId id = new PostLikeId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Embeddable
    @Getter @Setter @EqualsAndHashCode @NoArgsConstructor @AllArgsConstructor
    public static class PostLikeId implements Serializable {
        @Column(name = "post_id")
        private Long postId;

        @Column(name = "user_id")
        private Long userId;
    }
}
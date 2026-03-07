package com.mukunthan.nefra_connections.entity;

import com.mukunthan.nefra_connections.enums.ConnectionStatus;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "connections")
@Getter @Setter @NoArgsConstructor
public class Connection {

    @EmbeddedId
    private ConnectionId id = new ConnectionId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("investorId")
    @JoinColumn(name = "investor_id")
    private User investor;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("entrepreneurId")
    @JoinColumn(name = "entrepreneur_id")
    private User entrepreneur;

    @Enumerated(EnumType.STRING)
    private ConnectionStatus status = ConnectionStatus.PENDING;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Embeddable
    @Getter @Setter @EqualsAndHashCode @NoArgsConstructor @AllArgsConstructor
    public static class ConnectionId implements Serializable {
        @Column(name = "investor_id")
        private Long investorId;

        @Column(name = "entrepreneur_id")
        private Long entrepreneurId;
    }
}
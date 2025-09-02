package com.smartbeaver.insightly.dao.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


import java.time.OffsetDateTime;
import java.util.UUID;


@Entity
@Table(name = "invitations")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Invitation {
    @Id
    @GeneratedValue
    @Column(name = "invite_id")
    private UUID inviteId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    private Client client;


    @Column(nullable = false)
    private String email;


    @Column(nullable = false)
    private String role; // owner, admin, analyst, creator, viewer


    @Column(nullable = false)
    private String status; // pending, accepted, expired


    @Column(nullable = false)
    private String token;


    @CreationTimestamp
    @Column(name = "created_at")
    private OffsetDateTime createdAt;


    @Column(name = "expires_at")
    private OffsetDateTime expiresAt;
}
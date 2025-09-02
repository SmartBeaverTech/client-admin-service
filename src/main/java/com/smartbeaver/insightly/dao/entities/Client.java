package com.smartbeaver.insightly.dao.entities;

import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.CreationTimestamp;


import java.time.OffsetDateTime;
import java.util.UUID;


@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
    @Id
    @GeneratedValue
    @Column(name = "org_id")
    private UUID orgId;


    @Column(nullable = false)
    private String name;


    private String website;
    private String plan = "free";
    private String status = "active";


    @CreationTimestamp
    @Column(name = "created_at")
    private OffsetDateTime createdAt;
}
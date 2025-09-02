package com.smartbeaver.insightly.dao.entities;

import jakarta.persistence.*;
import lombok.*;


import java.util.UUID;


@Entity
@Table(name = "client_contacts")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ClientContact {
    @Id
    @GeneratedValue
    @Column(name = "contact_id")
    private UUID contactId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    private Client client;


    private String name;
    private String email;
    private String phone;
}
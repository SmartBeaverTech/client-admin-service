package com.smartbeaver.insightly.dao.entities;

import jakarta.persistence.*;
import lombok.*;


import java.util.UUID;


@Entity
@Table(name = "client_addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientAddress {
    @Id
    @GeneratedValue
    @Column(name = "address_id")
    private UUID addressId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    private Client client;


    private String line1;
    private String line2;
    private String city;
    private String state;
    private String zip;
    private String country;
}

package com.smartbeaver.insightly.dao.entities;

import jakarta.persistence.*;
import lombok.*;


import java.util.UUID;
@Entity
@Table(name = "payment_profiles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentProfile {
    @Id
    @GeneratedValue
    @Column(name = "payment_id")
    private UUID paymentId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    private Client client;


    private String provider; // e.g., stripe
    private String customerRef; // stripe_customer_id
    private String poNumber;
    private String status;
}
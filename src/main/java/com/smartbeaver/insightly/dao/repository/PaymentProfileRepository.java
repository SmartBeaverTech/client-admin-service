package com.smartbeaver.insightly.dao.repository;

import com.smartbeaver.insightly.dao.entities.PaymentProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentProfileRepository extends JpaRepository<PaymentProfile, UUID> {
    Optional<PaymentProfile> findByClient_OrgId(UUID orgId);
}



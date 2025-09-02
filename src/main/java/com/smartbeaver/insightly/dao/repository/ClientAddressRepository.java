package com.smartbeaver.insightly.dao.repository;

import com.smartbeaver.insightly.dao.entities.ClientAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientAddressRepository extends JpaRepository<ClientAddress, UUID> {
    Optional<ClientAddress> findByClient_OrgId(UUID orgId);
}

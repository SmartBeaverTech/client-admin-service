package com.smartbeaver.insightly.dao.repository;

import com.smartbeaver.insightly.dao.entities.ClientContact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientContactRepository extends JpaRepository<ClientContact, UUID> {
    Optional<ClientContact> findByClient_OrgId(UUID orgId);
}

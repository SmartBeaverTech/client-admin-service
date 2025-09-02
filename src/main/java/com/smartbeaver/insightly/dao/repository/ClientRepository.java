package com.smartbeaver.insightly.dao.repository;

import com.smartbeaver.insightly.dao.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface ClientRepository extends JpaRepository<Client, UUID> {}

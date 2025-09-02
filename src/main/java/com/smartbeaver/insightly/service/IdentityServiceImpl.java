package com.smartbeaver.insightly.service;

import org.springframework.stereotype.Component;


import java.util.UUID;


/**
 * Replace with real HTTP client to Identity Service (OIDC/Auth).
 */
@Component
public class IdentityServiceImpl implements IdentityService {

    public UUID createUserAndAssignOwner(UUID orgId, String email, String password) {
    // TODO: call identity-service
        return UUID.randomUUID();
    }


    public UUID createUserAndAssignRole(UUID orgId, String email, String name, String password, String role) {
    // TODO: call identity-service
        return UUID.randomUUID();
    }
}
package com.smartbeaver.insightly.service;

import java.util.UUID;

public interface IdentityService {
    public UUID createUserAndAssignOwner(UUID orgId, String email, String password);

    public UUID createUserAndAssignRole(UUID orgId, String email, String name, String password, String role);
}

package com.smartbeaver.insightly.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


import java.time.OffsetDateTime;
import java.util.UUID;


public class InvitationDto {
    public record CreateInvitationRequest(
            @NotBlank UUID orgId,
            @Email @NotBlank String email,
            @NotBlank String role
    ) {}


    public record InvitationResponse(
            UUID inviteId,
            String email,
            String role,
            String status,
            String token,
            OffsetDateTime expiresAt
    ) {}


    public record AcceptInvitationRequest(
            @NotBlank String name,
            @NotBlank String password
    ) {}
}

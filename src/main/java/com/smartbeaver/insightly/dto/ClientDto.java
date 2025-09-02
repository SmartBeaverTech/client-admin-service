package com.smartbeaver.insightly.dto;


import jakarta.validation.constraints.*;
import java.util.UUID;


public class ClientDto{
    // === Requests ===
    public record RegisterClientRequest(
            @NotBlank String companyName,
            @Pattern(regexp = "^https?://.*", message = "website must be a URL")
            String website,
            @NotBlank String addressLine1,
            String addressLine2,
            @NotBlank String city,
            @NotBlank String state,
            @NotBlank String zip,
            @NotBlank String country,
            String paymentProvider,
            String poNumber,
            @NotBlank String pocName,
            @Email @NotBlank String pocEmail,
            @NotBlank String pocPhone,
            @Email @NotBlank String ownerEmail,
            @NotBlank String ownerPassword
    ) {}


    // === Responses ===
    public record ClientSummary(
            UUID orgId,
            String name,
            String website,
            String plan,
            String status
    ) {}


    public record ClientProfile(
            UUID orgId,
            String name,
            String website,
            String plan,
            String status,
            Address address,
            Contact poc,
            Payment payment
    ) {}


    public record Address(String line1, String line2, String city, String state, String zip, String country) {}
    public record Contact(String name, String email, String phone) {}
    public record Payment(String provider, String customerRef, String poNumber, String status) {}
}
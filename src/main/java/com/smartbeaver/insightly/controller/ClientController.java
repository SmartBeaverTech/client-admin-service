package com.smartbeaver.insightly.controller;

import com.smartbeaver.insightly.dto.ClientDto;
import com.smartbeaver.insightly.dto.InvitationDto;
import com.smartbeaver.insightly.service.ClientServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
@Tag(name = "Client Management", description = "Register and manage clients")
public class ClientController {

    private final ClientServiceImpl clientService;

    @PostMapping("/register")
    @Operation(summary = "Register a new client", description = "Registers client with address, contact, payment, and owner account")
    public ResponseEntity<ClientDto.ClientSummary> register(
            @Valid @RequestBody ClientDto.RegisterClientRequest req
    ) {
        return ResponseEntity.ok(clientService.register(req));
    }

    @GetMapping("/{orgId}")
    @Operation(summary = "Get client profile", description = "Fetches profile details of the client by orgId")
    public ResponseEntity<ClientDto.ClientProfile> getProfile(@PathVariable UUID orgId) {
        return ResponseEntity.ok(clientService.getProfile(orgId));
    }

    @PostMapping("/invitations")
    @Operation(summary = "Send invitation", description = "Sends an invitation to join the client org")
    public ResponseEntity<InvitationDto.InvitationResponse> sendInvitation(
            @Valid @RequestBody InvitationDto.CreateInvitationRequest request
    ) {
        InvitationDto.InvitationResponse response = clientService.sendInvitation(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{orgId}")
    @Operation(summary = "Update client", description = "Updates client details")
    public ResponseEntity<ClientDto.ClientProfile> updateClient(
            @PathVariable UUID orgId,
            @Valid @RequestBody ClientDto.RegisterClientRequest request
    ) {
        return ResponseEntity.ok(clientService.updateClient(orgId, request));
    }

    @GetMapping
    @Operation(summary = "Get all clients", description = "Fetches summaries of all clients")
    public ResponseEntity<List<ClientDto.ClientSummary>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }
}

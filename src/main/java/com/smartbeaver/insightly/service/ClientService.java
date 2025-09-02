package com.smartbeaver.insightly.service;

import com.smartbeaver.insightly.dto.ClientDto;
import com.smartbeaver.insightly.dto.InvitationDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface ClientService {
    @Transactional
    ClientDto.ClientSummary register(ClientDto.RegisterClientRequest req);

    ClientDto.ClientProfile getProfile(UUID orgId);

    InvitationDto.InvitationResponse sendInvitation(InvitationDto.CreateInvitationRequest request);

    ClientDto.ClientProfile updateClient(UUID orgId, ClientDto.RegisterClientRequest request);

    List<ClientDto.ClientSummary> getAllClients();
}

package com.smartbeaver.insightly.service;


import com.smartbeaver.insightly.dao.entities.*;
import com.smartbeaver.insightly.dao.repository.*;

import com.smartbeaver.insightly.dto.ClientDto;
import com.smartbeaver.insightly.dto.InvitationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepo;
    private final ClientAddressRepository addressRepo;
    private final ClientContactRepository contactRepo;
    private final PaymentProfileRepository paymentRepo;
    private final IdentityService identityClient;
    private final TokenGeneratorService tokenGeneratorService;
    private final InvitationRepository invitationRepo;


    @Transactional
    @Override
    public ClientDto.ClientSummary register(ClientDto.RegisterClientRequest req) {
        Client client = Client.builder()
                .name(req.companyName())
                .website(req.website())
                .plan("free")
                .status("active")
                .build();
        client = clientRepo.save(client);


        ClientAddress addr = ClientAddress.builder()
                .client(client)
                .line1(req.addressLine1())
                .line2(req.addressLine2())
                .city(req.city())
                .state(req.state())
                .zip(req.zip())
                .country(req.country())
                .build();
        addressRepo.save(addr);


        ClientContact poc = ClientContact.builder()
                .client(client)
                .name(req.pocName())
                .email(req.pocEmail())
                .phone(req.pocPhone())
                .build();
        contactRepo.save(poc);


        PaymentProfile payment = PaymentProfile.builder()
                .client(client)
                .provider(req.paymentProvider())
                .poNumber(req.poNumber())
                .status("active")
                .build();
        paymentRepo.save(payment);


        // Identity: create Owner user
        UUID ownerUserId = identityClient.createUserAndAssignOwner(
                client.getOrgId(), req.ownerEmail(), req.ownerPassword());


        return new ClientDto.ClientSummary(client.getOrgId(), client.getName(), client.getWebsite(), client.getPlan(), client.getStatus());
    }

    @Override
    public ClientDto.ClientProfile getProfile(UUID orgId) {
        Client client = clientRepo.findById(orgId).orElseThrow(() -> new IllegalArgumentException("org not found"));


        ClientAddress addr = addressRepo.findAll().stream().filter(a -> a.getClient().getOrgId().equals(orgId)).findFirst().orElse(null);
        ClientContact poc = contactRepo.findAll().stream().filter(c -> c.getClient().getOrgId().equals(orgId)).findFirst().orElse(null);
        PaymentProfile pay = paymentRepo.findAll().stream().filter(p -> p.getClient().getOrgId().equals(orgId)).findFirst().orElse(null);


        ClientDto.Address a = addr == null ? null : new ClientDto.Address(addr.getLine1(), addr.getLine2(), addr.getCity(), addr.getState(), addr.getZip(), addr.getCountry());
        ClientDto.Contact c = poc == null ? null : new ClientDto.Contact(poc.getName(), poc.getEmail(), poc.getPhone());
        ClientDto.Payment p = pay == null ? null : new ClientDto.Payment(pay.getProvider(), pay.getCustomerRef(), pay.getPoNumber(), pay.getStatus());


        return new ClientDto.ClientProfile(orgId, client.getName(), client.getWebsite(), client.getPlan(), client.getStatus(), a, c, p);
    }

    @Override
    @Transactional
    public InvitationDto.InvitationResponse sendInvitation(InvitationDto.CreateInvitationRequest request) {
        Client client = clientRepo.findById(request.orgId())
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + request.orgId()));

        String token = tokenGeneratorService.generateUrlSafeToken(32);

        Invitation invitation = Invitation.builder()
                .client(client)
                .email(request.email())
                .role(request.role())
                .status("pending")
                .token(token)
                .expiresAt(OffsetDateTime.now().plusDays(7))
                .build();

        invitationRepo.save(invitation);

        return new InvitationDto.InvitationResponse(
                invitation.getInviteId(),
                invitation.getEmail(),
                invitation.getRole(),
                invitation.getStatus(),
                invitation.getToken(),
                invitation.getExpiresAt()
        );
    }

    @Override
    @Transactional
    public ClientDto.ClientProfile updateClient(UUID orgId, ClientDto.RegisterClientRequest request) {
        // --- Client ---
        Client client = clientRepo.findById(orgId)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + orgId));
        client.setName(request.companyName());
        client.setWebsite(request.website());
        clientRepo.save(client);

        // --- Address ---
        ClientAddress address = addressRepo.findByClient_OrgId(orgId)
                .orElseGet(() -> ClientAddress.builder().client(client).build());
        address.setLine1(request.addressLine1());
        address.setLine2(request.addressLine2());
        address.setCity(request.city());
        address.setState(request.state());
        address.setZip(request.zip());
        address.setCountry(request.country());
        addressRepo.save(address);

        // --- POC Contact ---
        ClientContact contact = contactRepo.findByClient_OrgId(orgId)
                .orElseGet(() -> ClientContact.builder().client(client).build());
        contact.setName(request.pocName());
        contact.setEmail(request.pocEmail());
        contact.setPhone(request.pocPhone());
        contactRepo.save(contact);

        // --- Payment ---
        PaymentProfile payment = paymentRepo.findByClient_OrgId(orgId)
                .orElseGet(() -> PaymentProfile.builder().client(client).build());
        payment.setProvider(request.paymentProvider());
        payment.setPoNumber(request.poNumber());
        paymentRepo.save(payment);

        // --- Map back to DTO ---
        return new ClientDto.ClientProfile(
                client.getOrgId(),
                client.getName(),
                client.getWebsite(),
                client.getPlan(),
                client.getStatus(),
                new ClientDto.Address(
                        address.getLine1(),
                        address.getLine2(),
                        address.getCity(),
                        address.getState(),
                        address.getZip(),
                        address.getCountry()
                ),
                new ClientDto.Contact(
                        contact.getName(),
                        contact.getEmail(),
                        contact.getPhone()
                ),
                new ClientDto.Payment(
                        payment.getProvider(),
                        payment.getCustomerRef(),
                        payment.getPoNumber(),
                        payment.getStatus()
                )
        );
    }

    @Override
    public List<ClientDto.ClientSummary> getAllClients() {
        return clientRepo.findAll().stream()
                .map(client -> new ClientDto.ClientSummary(
                        client.getOrgId(),
                        client.getName(),
                        client.getWebsite(),
                        client.getPlan(),
                        client.getStatus()
                ))
                .collect(Collectors.toList());
    }
}

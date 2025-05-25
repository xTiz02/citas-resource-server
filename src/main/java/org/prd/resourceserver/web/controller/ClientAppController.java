package org.prd.resourceserver.web.controller;

import org.prd.resourceserver.persistence.entity.ClientApp;
import org.prd.resourceserver.persistence.repository.ClientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("client")
@CrossOrigin(origins = "http://localhost:8085")
public class ClientAppController {

    private final ClientRepository clientRepository;

    public ClientAppController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping("/find/{clientId}")
    public ResponseEntity<ClientApp> getClientById(@PathVariable String clientId) {
        return ResponseEntity.ok(clientRepository.findByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found")));
    }
}
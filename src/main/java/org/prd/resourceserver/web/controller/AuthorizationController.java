package org.prd.resourceserver.web.controller;

import org.prd.resourceserver.persistence.entity.Authorization;
import org.prd.resourceserver.persistence.repository.AuthorizationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authorization")
@CrossOrigin(origins = "http://localhost:8085")
public class AuthorizationController {

    private final AuthorizationRepository authorizationRepository;

    public AuthorizationController(AuthorizationRepository authorizationRepository) {
        this.authorizationRepository = authorizationRepository;
    }

    @GetMapping("/findByToken")
    public ResponseEntity<Authorization> findAuthorizationByToken(@RequestHeader("Authorization-Token") String token) {
        return ResponseEntity.ok(
            authorizationRepository.findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValueOrOidcIdTokenValueOrUserCodeValueOrDeviceCodeValue(token)
                .orElseThrow(() -> new RuntimeException("Authorization not found for token: " + token))
        );
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Authorization> findAuthorizationById(@PathVariable String id) {
        return ResponseEntity.ok(
            authorizationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Authorization not found for id: " + id))
        );
    }

    @PostMapping("/create")
    public ResponseEntity<Authorization> createAuthorization(@RequestBody Authorization authorization) {
        Authorization savedAuthorization = authorizationRepository.save(authorization);
        return ResponseEntity.ok(savedAuthorization);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteAuthorization(@PathVariable String id) {
        authorizationRepository.deleteById(id);
        return ResponseEntity.ok("Authorization deleted successfully with id: " + id);
    }

}
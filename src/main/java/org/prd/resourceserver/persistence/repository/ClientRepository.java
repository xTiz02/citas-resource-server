package org.prd.resourceserver.persistence.repository;


import org.prd.resourceserver.persistence.entity.ClientApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientApp, Long> {
    Optional<ClientApp> findByClientId(String clientId);
}
package org.prd.resourceserver.persistence.repository;

import org.prd.resourceserver.persistence.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    // Additional query methods can be defined here if needed
}
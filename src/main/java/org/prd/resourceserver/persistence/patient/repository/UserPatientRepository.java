package org.prd.resourceserver.persistence.patient.repository;

import org.prd.resourceserver.persistence.patient.entity.UserPatient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPatientRepository extends JpaRepository<UserPatient,Long> {

  UserPatient findByUserId(Long userId);
}

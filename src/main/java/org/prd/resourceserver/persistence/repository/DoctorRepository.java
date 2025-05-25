package org.prd.resourceserver.persistence.repository;

import org.prd.resourceserver.persistence.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}
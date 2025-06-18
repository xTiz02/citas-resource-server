package org.prd.resourceserver.persistence.repository;

import org.prd.resourceserver.persistence.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}
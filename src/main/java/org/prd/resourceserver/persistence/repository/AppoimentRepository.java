package org.prd.resourceserver.persistence.repository;

import org.prd.resourceserver.persistence.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppoimentRepository extends JpaRepository<Appointment,Long> {

}
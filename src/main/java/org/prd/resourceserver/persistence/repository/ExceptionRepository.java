package org.prd.resourceserver.persistence.repository;

import org.prd.resourceserver.persistence.entity.ScheduleException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExceptionRepository extends JpaRepository<ScheduleException, Long> {
    List<ScheduleException> findAllByDoctor_Id(Long doctorId);
}
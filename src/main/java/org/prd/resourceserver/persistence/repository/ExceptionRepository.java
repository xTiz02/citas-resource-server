package org.prd.resourceserver.persistence.repository;

import org.prd.resourceserver.persistence.entity.ScheduleException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExceptionRepository extends JpaRepository<ScheduleException, Long> {
    Page<ScheduleException> findAllByDoctor_Id(Long doctorId, Pageable pageable);

    List<ScheduleException> findAllByDoctor_Id(Long doctorId);
}
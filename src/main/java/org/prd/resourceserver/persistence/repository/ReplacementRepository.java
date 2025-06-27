package org.prd.resourceserver.persistence.repository;

import org.prd.resourceserver.persistence.entity.ScheduleReplacement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplacementRepository extends JpaRepository<ScheduleReplacement,Long> {

  Page<ScheduleReplacement> findPageBySchedule_Id(Pageable pageable,long scheduleId);
}
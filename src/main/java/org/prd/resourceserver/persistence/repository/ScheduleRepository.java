package org.prd.resourceserver.persistence.repository;

import java.time.LocalDate;
import java.util.List;
import org.prd.resourceserver.persistence.entity.DoctorSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ScheduleRepository extends JpaRepository<DoctorSchedule, Long> {

  Page<DoctorSchedule> findAllByDoctor_Id(Long doctorId, Pageable pageable);

  @Query("""
          SELECT ds FROM doctor_schedule ds
          WHERE :fecha BETWEEN ds.startDate AND ds.endDate
          AND ds.enabled = true
      """)
  List<DoctorSchedule> findSchedulesActiveNow(@Param("fechaActual") LocalDate fecha);


  @Query("""
          SELECT ds FROM doctor_schedule ds
          WHERE ds.specialty.id = :specialtyId
            AND ds.enabled = true
            AND (:doctorId IS NULL OR ds.doctor.id = :doctorId)
            AND ds.endDate >= CURRENT_DATE
            AND (
                  :selectedDate IS NULL 
                  AND FUNCTION('MONTH', ds.startDate) = FUNCTION('MONTH', CURRENT_DATE)
                  AND FUNCTION('YEAR', ds.startDate) = FUNCTION('YEAR', CURRENT_DATE)
               OR
                  :selectedDate IS NOT NULL 
                  AND FUNCTION('DATE', ds.startDate) = :selectedDate
               )
      """)
  List<DoctorSchedule> findSchedules(
      @Param("specialtyId") Long specialtyId,
      @Param("doctorId") Long doctorId,
      @Param("selectedDate") LocalDate selectedDate
  );
}
package org.prd.resourceserver.persistence.repository;

import java.util.List;
import org.prd.resourceserver.persistence.entity.RescheduledAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RescheduleRepository extends JpaRepository<RescheduledAppointment,Long> {

  @Query("""
        SELECT ra
        FROM   rescheduled_appointment ra
        WHERE  ra.schedule.id = :scheduleId
          AND  ra.orderNumber = (
                 SELECT MAX(ra2.orderNumber)
                 FROM   rescheduled_appointment ra2
                 WHERE  ra2.originalAppointment.id = ra.originalAppointment.id
               )
    """)
  List<RescheduledAppointment> findLatestPerAppointmentBySchedule(@Param("scheduleId") Long scheduleId);

}
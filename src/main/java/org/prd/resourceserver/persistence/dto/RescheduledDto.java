package org.prd.resourceserver.persistence.dto;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.prd.resourceserver.persistence.entity.Appointment;
import org.prd.resourceserver.persistence.entity.DoctorSchedule;

public record RescheduledDto(
    Long id,
    LocalDate newDate,
    String startTime,
    int duration,
    SchedulePageDto schedule,
    int orderNumber,
    Date createdAt
) {

}
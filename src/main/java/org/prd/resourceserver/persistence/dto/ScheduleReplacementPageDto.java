package org.prd.resourceserver.persistence.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.prd.resourceserver.persistence.entity.Doctor;
import org.prd.resourceserver.util.TurnEnum;

public record ScheduleReplacementPageDto(
    Long id,
    DoctorPageDto absentDoctor,
    DoctorPageDto coveringDoctor,
    LocalDate dateFrom,
    LocalDate dateTo,
    Date createdAt,
    Date updatedAt,
    SchedulePageDto schedule
) {

}
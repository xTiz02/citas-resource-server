package org.prd.resourceserver.persistence.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.prd.resourceserver.util.AppointmentStatus;

public record AppointmentPageDto(
    Long id,
    LocalDate date,
    LocalDate cancelDate,
    String startTime,
    SpecialtyPageDto specialty,
    PatientPageDto patient,
    DoctorPageDto doctor,
    SchedulePageDto schedule,
    AppointmentStatus status,
    Date createdAt,
    Date updatedAt,
    boolean isCancelled,
    boolean isRescheduled,
    List<RescheduledDto> rescheduledAppointments,
    UserPageDto createdBy,
    boolean enabled
) {

}
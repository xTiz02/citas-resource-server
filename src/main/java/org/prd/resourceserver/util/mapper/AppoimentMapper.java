package org.prd.resourceserver.util.mapper;

import org.prd.resourceserver.persistence.dto.AppointmentPageDto;
import org.prd.resourceserver.persistence.entity.Appointment;

public class AppoimentMapper {
 public static AppointmentPageDto toPageDto(Appointment appointment) {
  return new AppointmentPageDto(
      appointment.getId(),
      appointment.getDate(),
      appointment.getCancelDate(),
      appointment.getStartTime(),
      SpecialtyMapper.toPageDto(appointment.getSpecialty()),
      PatientMapper.toPageDto(appointment.getPatient(),null),
      DoctorMapper.toPageDto(appointment.getDoctor()),
      ScheduleMapper.toPageDto(appointment.getSchedule()),
      appointment.getStatus(),
      appointment.getCreatedAt(),
      appointment.getUpdatedAt(),
      appointment.isCancelled(),
      appointment.isRescheduled(),
      appointment.getRescheduledAppointments().stream()
          .map(RescheduledMapper::toDto)
          .toList(),
      UserMapper.toPageDto(appointment.getCreatedBy()),
      appointment.isEnabled()
  );
 }
}
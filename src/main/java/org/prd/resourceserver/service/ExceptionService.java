package org.prd.resourceserver.service;

import java.util.List;
import org.prd.resourceserver.persistence.dto.AppointmentPageDto;
import org.prd.resourceserver.persistence.dto.CreateExceptionDto;
import org.prd.resourceserver.persistence.dto.PageResponse;
import org.prd.resourceserver.persistence.dto.ScheduleExceptionPageDto;
import org.springframework.data.domain.Pageable;

public interface ExceptionService {

  List<ScheduleExceptionPageDto> validateExceptionConflits(Long doctorId,
      CreateExceptionDto createExceptionDto);

  ScheduleExceptionPageDto createException(Long doctorId, CreateExceptionDto createExceptionDto);
  ScheduleExceptionPageDto updateException(Long doctorId, CreateExceptionDto createExceptionDto);
  List<AppointmentPageDto> validateAppointmentConflits(Long doctorId,
      CreateExceptionDto createScheduleDto);
  PageResponse<ScheduleExceptionPageDto> findAllByDoctorId(Long doctorId, Pageable pageable);
}
package org.prd.resourceserver.service;

import org.prd.resourceserver.persistence.dto.AppointmentPageDto;
import org.prd.resourceserver.persistence.dto.CreateScheduleDto;
import org.prd.resourceserver.persistence.dto.PageResponse;
import org.prd.resourceserver.persistence.dto.ScheduleExceptionPageDto;
import org.prd.resourceserver.persistence.dto.SchedulePageDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ScheduleService {

    SchedulePageDto createSchedule(CreateScheduleDto createScheduleDto);
    PageResponse<SchedulePageDto> getAllSchedulesByDoctor(Long id,Pageable pageable);
    SchedulePageDto updateSchedule(Long id, CreateScheduleDto createScheduleDto);
    List<SchedulePageDto> validateSchedule(Long existingScheduleId, CreateScheduleDto createScheduleDto);
    void deleteSchedule(Long scheduleId);
    List<AppointmentPageDto> validateAppointmentConflictsInUpdate(Long existingScheduleId, CreateScheduleDto createScheduleDto);
    void rescheduledAppointmentsOfSchedule(Long scheduleId);
    List<AppointmentPageDto> validateIfExistAppointmentNowInSchedule(Long scheduleId);
}
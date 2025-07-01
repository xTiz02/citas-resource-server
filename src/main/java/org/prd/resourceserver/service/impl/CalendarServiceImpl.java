package org.prd.resourceserver.service.impl;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.prd.resourceserver.persistence.dto.CalendarScheduleDto;
import org.prd.resourceserver.persistence.entity.DoctorSchedule;
import org.prd.resourceserver.persistence.repository.DoctorRepository;
import org.prd.resourceserver.persistence.repository.ReplacementRepository;
import org.prd.resourceserver.persistence.repository.RescheduleRepository;
import org.prd.resourceserver.persistence.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CalendarServiceImpl {

  private final ScheduleRepository scheduleRepository;
  private final ReplacementRepository replacementRepository;
  private final RescheduleRepository rescheduleRepository;
  private final DoctorRepository doctorRepository;

  public CalendarServiceImpl(ScheduleRepository scheduleRepository,
      ReplacementRepository replacementRepository, RescheduleRepository rescheduleRepository,
      DoctorRepository doctorRepository) {
    this.scheduleRepository = scheduleRepository;
    this.replacementRepository = replacementRepository;
    this.rescheduleRepository = rescheduleRepository;
    this.doctorRepository = doctorRepository;
  }


  public List<CalendarScheduleDto> findCalendarSchedulesByMonth(Long doctorId,@NotNull Long specialtyId, LocalDate date) {
    List<DoctorSchedule> schedulesOfTheMonth = scheduleRepository.findSchedules(
        specialtyId, doctorId, date);

    return null;
  }
}
package org.prd.resourceserver.service.impl;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.prd.resourceserver.persistence.dto.AppointmentPageDto;
import org.prd.resourceserver.persistence.dto.CreateScheduleDto;
import org.prd.resourceserver.persistence.dto.PageResponse;
import org.prd.resourceserver.persistence.dto.ScheduleExceptionPageDto;
import org.prd.resourceserver.persistence.dto.SchedulePageDto;
import org.prd.resourceserver.persistence.entity.*;
import org.prd.resourceserver.persistence.repository.*;
import org.prd.resourceserver.service.ScheduleService;
import org.prd.resourceserver.util.AppointmentStatus;
import org.prd.resourceserver.util.DayWeekEnum;
import org.prd.resourceserver.util.TurnEnum;
import org.prd.resourceserver.util.UtilConstants;
import org.prd.resourceserver.util.mapper.AppoimentMapper;
import org.prd.resourceserver.util.mapper.ScheduleExceptionMapper;
import org.prd.resourceserver.util.mapper.ScheduleMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ScheduleServiceImpl implements ScheduleService {

  private final DoctorRepository doctorRepository;
  private final ExceptionRepository exceptionRepository;
  private final PatientRepository patientRepository;
  private final SpecialtyRepository specialtyRepository;
  private final LocationRepository locationRepository;
  private final ScheduleRepository scheduleRepository;
  private final RescheduleRepository rescheduleRepository;

  public ScheduleServiceImpl(DoctorRepository doctorRepository,
      ExceptionRepository exceptionRepository,
      PatientRepository patientRepository,
      SpecialtyRepository specialtyRepository,
      LocationRepository locationRepository,
      ScheduleRepository scheduleRepository, RescheduleRepository rescheduleRepository) {
    this.doctorRepository = doctorRepository;
    this.exceptionRepository = exceptionRepository;
    this.patientRepository = patientRepository;
    this.specialtyRepository = specialtyRepository;
    this.locationRepository = locationRepository;
    this.scheduleRepository = scheduleRepository;
    this.rescheduleRepository = rescheduleRepository;
  }

  @Transactional
  @Override
  public SchedulePageDto createSchedule(CreateScheduleDto createScheduleDto) {

    validateSchedule(null, createScheduleDto);

    var doctorSchedule = ScheduleMapper.toEntity(createScheduleDto);
    doctorSchedule.setDoctor(new Doctor(createScheduleDto.doctorId()));
    doctorSchedule.setSpecialty(new Specialty(createScheduleDto.specialtyId()));
    doctorSchedule.setLocation(new Location(createScheduleDto.locationId()));
    doctorSchedule.setEnabled(true);
    doctorSchedule = scheduleRepository.save(doctorSchedule);

    return ScheduleMapper.toPageDto(doctorSchedule);
  }

  @Transactional(readOnly = true)
  @Override
  public PageResponse<SchedulePageDto> getAllSchedulesByDoctor(Long id, Pageable pageable) {
    var page = scheduleRepository.findAllByDoctor_Id(id, pageable);
    return new PageResponse<>(
        page.getContent().stream().map(ScheduleMapper::toPageDto).toList(),
        page.getNumber(),
        page.getSize(),
        page.getTotalElements(),
        page.getTotalPages(),
        page.isLast(),
        page.isFirst()
    );
  }

  @Override
  public SchedulePageDto updateSchedule(Long id, CreateScheduleDto createScheduleDto) {
//    validateSchedule(id, createScheduleDto);
    DoctorSchedule existingSchedule = scheduleRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Schedule not found"));
    ScheduleMapper.toEntity(existingSchedule, createScheduleDto);
//    existingSchedule.setDoctor(new Doctor(createScheduleDto.doctorId()));
//    existingSchedule.setSpecialty(new Specialty(createScheduleDto.specialtyId()));
    existingSchedule.setLocation(new Location(createScheduleDto.locationId()));

    return ScheduleMapper.toPageDto(scheduleRepository.save(existingSchedule));
  }

  @Override
  public List<SchedulePageDto> validateSchedule(Long existingScheduleId,
      CreateScheduleDto createScheduleDto) {
    log.info("Creating schedule: {}", createScheduleDto);
   if(existingScheduleId != null) {
      DoctorSchedule existingSchedule = scheduleRepository.findById(existingScheduleId)
          .orElseThrow(() -> new IllegalArgumentException("Schedule not found with id: " + existingScheduleId));
      LocalDate now = LocalDate.now();
      if((existingSchedule.getStartDate().isBefore(now) &&
          existingSchedule.getEndDate().isBefore(now)) || !existingSchedule.isEnabled()) {
        throw new IllegalArgumentException("Cannot update a schedule that is not active or has already ended");
      }
    }

    if (!UtilConstants.isValidDuration(createScheduleDto.duration())) {
      throw new IllegalArgumentException("Invalid duration. Valid durations are 15 or 30 minutes.");
    }

    if (createScheduleDto.startDate().isAfter(createScheduleDto.endDate())) {
      throw new IllegalArgumentException("Start date cannot be after end date");
    }
    if (createScheduleDto.endDate().isBefore(createScheduleDto.startDate())) {
      throw new IllegalArgumentException("End date cannot be before start date");
    }

    if (createScheduleDto.startDate().isBefore(LocalDate.now())) {
      throw new IllegalArgumentException("Start date cannot be in the past");
    }

//    LocalDate localDate1 = createScheduleDto.startDate().toInstant().atZone(ZoneId.systemDefault())
//        .toLocalDate();
//    LocalDate localDate2 = createScheduleDto.endDate().toInstant().atZone(ZoneId.systemDefault())
//        .toLocalDate();
//    log.info("Start date: {}, End date: {}", localDate1, localDate2);
    if (createScheduleDto.startDate().isEqual(createScheduleDto.endDate())) {
      throw new IllegalArgumentException("Start date cannot be the same as end date");
    }
    if (createScheduleDto.startTime().equals(createScheduleDto.endTime())) {
      throw new IllegalArgumentException("Start time cannot be the same as end time");
    }

    if (!UtilConstants.isValidTimes(createScheduleDto.startTime(), createScheduleDto.endTime())) {
      throw new IllegalArgumentException("Invalid start or end time format");
    }

    List<DoctorSchedule> conflicts = new ArrayList<>();

    scheduleRepository.findSchedulesActiveNow(LocalDate.now())
        .stream()
        .filter(schedule -> schedule.isEnabled() && !schedule.getId().equals(existingScheduleId))
        .forEach(schedule -> {
          if (UtilConstants.isDateRangeOverlapping(
              schedule.getStartDate(), schedule.getEndDate(),
              createScheduleDto.startDate(), createScheduleDto.endDate())) {

            if (createScheduleDto.dayOfTheWeek() == schedule.getDayOfTheWeek() ||
                createScheduleDto.dayOfTheWeek() == DayWeekEnum.WEEK) {
              if ((createScheduleDto.turn() == schedule.getTurn() ||
                  createScheduleDto.turn() == TurnEnum.ALL_DAY) &&
                  createScheduleDto.locationId().equals(schedule.getLocation()
                      .getId())) {
                LocalTime newStartTime = LocalTime.parse(createScheduleDto.startTime());
                LocalTime newEndTime = LocalTime.parse(createScheduleDto.endTime());
                LocalTime existingStartTime = LocalTime.parse(schedule.getStartTime());
                LocalTime existingEndTime = LocalTime.parse(schedule.getEndTime());

                boolean timeOverlap =
                    newStartTime.isBefore(existingEndTime) &&
                        newEndTime.isAfter(existingStartTime);

                if (timeOverlap) {
                  conflicts.add(schedule);
                }
              }
            }
          }
        });

    if (!conflicts.isEmpty()) {
      List<SchedulePageDto> conflictDtos = conflicts.stream()
          .map(ScheduleMapper::toPageDto)
          .toList();
      log.warn("Conflicting schedules found: {}", conflictDtos);
      return conflictDtos;
    } else {
      log.info("No conflicts found for the schedule: {}", createScheduleDto);
      return List.of();
    }

  }

  //solo cuando se reduce el horario del medico.
  public List<AppointmentPageDto> validateAppointmentConflicts(Long existingScheduleId, CreateScheduleDto createScheduleDto) {

    DoctorSchedule existingSchedule = scheduleRepository.findById(existingScheduleId)
        .orElseThrow(() -> new IllegalArgumentException("Schedule not found with id: " + existingScheduleId));
    LocalDate now = LocalDate.now();

    if(existingSchedule.getStartDate().isBefore(now) &&
        existingSchedule.getEndDate().isAfter(now) && existingSchedule.getEndDate().isBefore(createScheduleDto.endDate())) {
      return List.of();
    }

    if(existingSchedule.getStartDate().isAfter(now) &&
        existingSchedule.getStartDate().isEqual(createScheduleDto.startDate()) &&
        existingSchedule.getEndDate().isBefore(createScheduleDto.endDate())) {
      return List.of();
    }
    List<Appointment> reprogrammingAppointments = new ArrayList<>();

    existingSchedule.getAppointments().stream()
        .filter(Appointment::isEnabled)
        .filter(a -> !a.isCancelled())
        .filter(a -> a.getStatus() == AppointmentStatus.SHEDULED)
        .filter(a-> a.getDate().isBefore(createScheduleDto.startDate()) || a.getDate().isAfter(createScheduleDto.endDate()))
        .forEach(a -> {
          reprogrammingAppointments.add(a);
        });

    List<RescheduledAppointment> rescheduledAppointments =
        rescheduleRepository.findLatestPerAppointmentBySchedule(existingSchedule.getId());
    rescheduledAppointments.stream()
        .filter(ra -> ra.getNewDate().isBefore(createScheduleDto.startDate()) || ra.getNewDate().isAfter(createScheduleDto.endDate()))
        .forEach(ra -> {
          if (ra.getOriginalAppointment().isEnabled() && !ra.getOriginalAppointment().isCancelled()
              && ra.getOriginalAppointment().getStatus().equals(AppointmentStatus.RESHEDULED)) {
            reprogrammingAppointments.add(ra.getOriginalAppointment());
          }
        });
    if(!reprogrammingAppointments.isEmpty()) {
      return reprogrammingAppointments.stream()
          .map(AppoimentMapper::toPageDto)
          .collect(Collectors.toList());
    }else {
      return List.of();
    }
  }

  @Override
  public void deleteSchedule(int scheduleId) {

  }


}
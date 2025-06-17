package org.prd.resourceserver.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.prd.resourceserver.persistence.dto.AppointmentPageDto;
import org.prd.resourceserver.persistence.dto.CreateExceptionDto;
import org.prd.resourceserver.persistence.dto.PageResponse;
import org.prd.resourceserver.persistence.dto.ScheduleExceptionPageDto;
import org.prd.resourceserver.persistence.entity.Appointment;
import org.prd.resourceserver.persistence.entity.Doctor;
import org.prd.resourceserver.persistence.entity.DoctorSchedule;
import org.prd.resourceserver.persistence.entity.RescheduledAppointment;
import org.prd.resourceserver.persistence.entity.ScheduleException;
import org.prd.resourceserver.persistence.repository.DoctorRepository;
import org.prd.resourceserver.persistence.repository.ExceptionRepository;
import org.prd.resourceserver.persistence.repository.RescheduleRepository;
import org.prd.resourceserver.service.ExceptionService;
import org.prd.resourceserver.util.AppointmentStatus;
import org.prd.resourceserver.util.TurnEnum;
import org.prd.resourceserver.util.mapper.AppoimentMapper;
import org.prd.resourceserver.util.mapper.ScheduleExceptionMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ExceptionServiceImpl implements ExceptionService {

  private final DoctorRepository doctorRepository;
  private final ExceptionRepository exceptionRepository;
  private final RescheduleRepository rescheduleRepository;

  public ExceptionServiceImpl(DoctorRepository doctorRepository,
      ExceptionRepository exceptionRepository, RescheduleRepository rescheduleRepository) {
    this.doctorRepository = doctorRepository;
    this.exceptionRepository = exceptionRepository;
    this.rescheduleRepository = rescheduleRepository;
  }

  @Override
  public List<ScheduleExceptionPageDto> validateExceptionConflits(Long doctorId,
      CreateExceptionDto createExceptionDto) {

    if (createExceptionDto.dateException().isBefore(LocalDate.now())) {
      throw new IllegalArgumentException("Exception date cannot be in the past");
    }

    if( createExceptionDto.dateException().isEqual(LocalDate.now())) {
      throw new IllegalArgumentException("Exception date cannot be today");
    }

    List<ScheduleException> confilts = new ArrayList<>();
    List<ScheduleException> existingExceptions = exceptionRepository.findAllByDoctor_Id(doctorId);
    existingExceptions.stream()
        .filter(exception -> !exception.getId().equals(createExceptionDto.exceptionId())).forEach(
            exception -> {
              if (exception.getDateException().isEqual(createExceptionDto.dateException())
                  && exception.getTurn() == createExceptionDto.turn()) {
                confilts.add(exception);
              }
            }
        );

    if (!confilts.isEmpty()) {
      return confilts.stream()
          .map(ScheduleExceptionMapper::toPageDto)
          .toList();
    } else {
      return List.of();
    }
  }

  @Override
  public List<AppointmentPageDto> validateAppointmentConflits(Long doctorId,
      CreateExceptionDto createScheduleDto) {
    if(createScheduleDto.dateException().isBefore(LocalDate.now())) {
      throw new IllegalArgumentException("Exception date cannot be in the past");
    }

    Doctor doctor = doctorRepository.findById(doctorId)
        .orElseThrow(() -> new IllegalArgumentException("Doctor not found with id: " + doctorId));

    List<DoctorSchedule> doctorSchedulesConfilts = doctor.getDoctorScheduleList().stream()
        .filter(DoctorSchedule::isEnabled)
        .filter(schedule -> schedule.getStartDate().isBefore(createScheduleDto.dateException())
            && schedule.getEndDate().isAfter(createScheduleDto.dateException()))
        .filter(schedule -> schedule.getTurn() == createScheduleDto.turn()
            || createScheduleDto.turn() == TurnEnum.ALL_DAY)
        .toList();

    List<Appointment> rescheduleAppointments = new ArrayList<>();

    if (!doctorSchedulesConfilts.isEmpty()) {
      doctorSchedulesConfilts.forEach(
          schedule -> {
            List<Appointment> appointmentsConflicts = new ArrayList<>();
            schedule.getAppointments()
                .stream()
                .filter(Appointment::isEnabled)
                .filter(appointment -> !appointment.isCancelled())
                .filter(appointment -> appointment.getStatus().equals(AppointmentStatus.SHEDULED))
                .forEach(appointment -> {
                  appointmentsConflicts.add(appointment);
                });
            List<RescheduledAppointment> rescheduledAppointments =
                rescheduleRepository.findLatestPerAppointmentBySchedule(schedule.getId());
            rescheduledAppointments.stream()
                .filter(rescheduled -> rescheduled.getNewDate().isEqual(createScheduleDto.dateException())
                    && (createScheduleDto.turn() == TurnEnum.ALL_DAY
                        || rescheduled.getSchedule().getTurn() == createScheduleDto.turn()))
                .forEach(rescheduled -> {
                  Appointment originalAppointment = rescheduled.getOriginalAppointment();
                  if (originalAppointment.isEnabled() && !originalAppointment.isCancelled()
                      && originalAppointment.getStatus().equals(AppointmentStatus.RESHEDULED)) {
                    appointmentsConflicts.add(originalAppointment);
                  }
                });
            if (!appointmentsConflicts.isEmpty()) {
              rescheduleAppointments.addAll(appointmentsConflicts);
            }
          }
      );
    }

    if (!rescheduleAppointments.isEmpty()) {
      return rescheduleAppointments.stream()
          .map(AppoimentMapper::toPageDto)
          .collect(Collectors.toList());
    } else {
      return List.of();
    }
  }

  @Override
  public ScheduleExceptionPageDto createException(Long doctorId,
      CreateExceptionDto createExceptionDto) {
    Doctor doctor = doctorRepository.findById(doctorId)
        .orElseThrow(() -> new IllegalArgumentException("Doctor not found with id: " + doctorId));

    Set<DoctorSchedule> doctorSchedulesAfected = doctor.getDoctorScheduleList()
        .stream()
        .filter(schedule -> schedule.getStartDate().isBefore(createExceptionDto.dateException())
            && schedule.getEndDate().isAfter(createExceptionDto.dateException()))
        .filter(schedule -> schedule.getTurn() == createExceptionDto.turn()
            || createExceptionDto.turn() == TurnEnum.ALL_DAY)
        .collect(Collectors.toSet());

    ScheduleException exception = ScheduleException.builder()
        .dateException(createExceptionDto.dateException())
        .turn(createExceptionDto.turn())
        .doctor(new Doctor(doctor.getId()))
        .build();

    if (!doctorSchedulesAfected.isEmpty()) {
      exception.getSchedulesAfected().clear();
      exception.getSchedulesAfected().addAll(doctorSchedulesAfected);
    }

    return ScheduleExceptionMapper.toPageDto(exceptionRepository.save(exception));
  }

  @Override
  public ScheduleExceptionPageDto updateException(Long doctorId,
      CreateExceptionDto createExceptionDto) {
    Doctor doctor = doctorRepository.findById(doctorId)
        .orElseThrow(() -> new IllegalArgumentException("Doctor not found with id: " + doctorId));

    ScheduleException exception = exceptionRepository.findById(createExceptionDto.exceptionId())
        .orElseThrow(() -> new IllegalArgumentException(
            "Exception not found with id: " + createExceptionDto.exceptionId()));

    Set<DoctorSchedule> doctorSchedulesAfected = doctor.getDoctorScheduleList()
        .stream()
        .filter(schedule -> schedule.getStartDate().isBefore(createExceptionDto.dateException())
            && schedule.getEndDate().isAfter(createExceptionDto.dateException()))
        .filter(schedule -> schedule.getTurn() == createExceptionDto.turn()
            || createExceptionDto.turn() == TurnEnum.ALL_DAY)
        .collect(Collectors.toSet());
    if (!doctorSchedulesAfected.isEmpty()) {
      exception.getSchedulesAfected().clear();
      exception.setSchedulesAfected(doctorSchedulesAfected);
    }

    exception.setDateException(createExceptionDto.dateException());
    exception.setTurn(createExceptionDto.turn());
    exception.setDoctor(new Doctor(doctor.getId()));

    return ScheduleExceptionMapper.toPageDto(exceptionRepository.save(exception));
  }

  @Override
  public PageResponse<ScheduleExceptionPageDto> findAllByDoctorId(Long doctorId, Pageable pageable) {
    Doctor doctor = doctorRepository.findById(doctorId)
        .orElseThrow(() -> new IllegalArgumentException("Doctor not found with id: " + doctorId));

    Page<ScheduleException> exceptions = exceptionRepository.findAllByDoctor_Id(doctorId, pageable);

    return new PageResponse<>(
        exceptions.stream().map(ScheduleExceptionMapper::toPageDto).toList(),
        pageable.getPageNumber(),
        pageable.getPageSize(),
        exceptions.getTotalElements(),
        exceptions.getTotalPages(),
        exceptions.isLast(),
        exceptions.isFirst()
    );
  }
}
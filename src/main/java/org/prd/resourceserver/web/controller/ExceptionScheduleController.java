package org.prd.resourceserver.web.controller;

import java.util.List;
import org.prd.resourceserver.persistence.dto.AppointmentPageDto;
import org.prd.resourceserver.persistence.dto.CreateExceptionDto;
import org.prd.resourceserver.persistence.dto.PageResponse;
import org.prd.resourceserver.persistence.dto.ScheduleExceptionPageDto;
import org.prd.resourceserver.service.ExceptionService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class ExceptionScheduleController {

  private final ExceptionService exceptionService;

  public ExceptionScheduleController(ExceptionService exceptionService) {
    this.exceptionService = exceptionService;
  }

  @GetMapping("/all/{doctorId}")
  public ResponseEntity<PageResponse<ScheduleExceptionPageDto>> getAllExceptionsByDoctorId(
      @PathVariable Long doctorId, Pageable pageable) {
    return ResponseEntity.ok(exceptionService.findAllByDoctorId(doctorId,pageable));
  }

  @PostMapping("/validate/{doctorId}")
  public ResponseEntity<List<ScheduleExceptionPageDto>> validateExceptionConflicts(
      @PathVariable Long doctorId, @RequestBody  CreateExceptionDto createExceptionDto) {
    List<ScheduleExceptionPageDto> conflicts = exceptionService.validateExceptionConflits(doctorId, createExceptionDto);
    return ResponseEntity.ok(conflicts);
  }

  @PostMapping("/validate/appointment/{doctorId}")
  public ResponseEntity<List<AppointmentPageDto>> validateAppointmentConflictsToReschedule(
      @PathVariable Long doctorId, @RequestBody CreateExceptionDto createExceptionDto) {
    List<AppointmentPageDto> appointmentConflicts = exceptionService.validateAppointmentConflits(doctorId, createExceptionDto);
    return ResponseEntity.ok(appointmentConflicts);
  }

  @PostMapping("/create/{doctorId}")
  public ResponseEntity<ScheduleExceptionPageDto> createException(
      @PathVariable Long doctorId, @RequestBody CreateExceptionDto createExceptionDto) {
    ScheduleExceptionPageDto createdException = exceptionService.createException(doctorId, createExceptionDto);
    return ResponseEntity.ok(createdException);
  }

  @PutMapping("/update/{doctorId}")
  public ResponseEntity<ScheduleExceptionPageDto> updateException(
      @PathVariable Long doctorId, @RequestBody CreateExceptionDto createExceptionDto) {
    ScheduleExceptionPageDto updatedException = exceptionService.updateException(doctorId, createExceptionDto);
    return ResponseEntity.ok(updatedException);
  }

}
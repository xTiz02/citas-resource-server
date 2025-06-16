package org.prd.resourceserver.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import org.prd.resourceserver.persistence.dto.PageResponse;
import org.prd.resourceserver.persistence.dto.PatientPageDto;
import org.prd.resourceserver.persistence.entity.Appointment;
import org.prd.resourceserver.persistence.entity.Patient;
import org.prd.resourceserver.persistence.repository.PatientRepository;
import org.prd.resourceserver.service.PatientService;
import org.prd.resourceserver.util.mapper.PatientMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PatientServiceImpl implements PatientService {

  private final PatientRepository patientRepository;

  public PatientServiceImpl(PatientRepository patientRepository) {
    this.patientRepository = patientRepository;
  }

  @Override
  public PageResponse<PatientPageDto> getAllPatients(Pageable pageable) {
    Page<Patient> page = patientRepository.findAll(pageable);

    return new PageResponse<>(
        page.getContent().stream().map(
            patient -> {
              Date lastVisit = null;
              if (!patient.getAppointments().isEmpty()) {
                lastVisit = patient.getAppointments().stream()
                    .map( appointment -> Date.from(appointment.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()))
                    .max(Date::compareTo)
                    .orElse(null);
              }
              return PatientMapper.toPageDto(patient, lastVisit);
            }
        ).toList(),
        page.getNumber(),
        page.getSize(),
        page.getTotalElements(),
        page.getTotalPages(),
        page.isLast(),
        page.isFirst()
    );
  }
}
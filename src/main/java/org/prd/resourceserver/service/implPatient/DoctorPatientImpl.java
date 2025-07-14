package org.prd.resourceserver.service.implPatient;

import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.prd.resourceserver.persistence.patient.dto.ApiResponse;
import org.prd.resourceserver.persistence.patient.dto.DoctorPatientDto;
import org.prd.resourceserver.util.DoctorExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DoctorPatientImpl {

  @Autowired
  private DoctorExample doctorExample;


  public ApiResponse<DoctorPatientDto> getDoctorAvailability(Long doctorId, int mes){
    log.info("Fetching doctors for doctor ID: " + doctorId + " and month: " + mes);

    List<DoctorPatientDto> doctors = doctorExample.getDoctors().stream()
        .filter(doctor -> doctor.id().equals(doctorId) && doctor.mes() == mes)
        .toList();

    if (doctors.isEmpty()) {
      return new ApiResponse<>("No doctors found for the given criteria", null, null, true);
    }

    return new ApiResponse<>("Doctors found", null, doctors.get(0), true);
  }

  public ApiResponse<List<DoctorPatientDto>> getDoctorsBySpecialty(String specialty) {
    log.info("Fetching doctors for specialty: " + specialty);
    List<DoctorPatientDto> doctors = doctorExample.getDoctors().stream()
        .filter(doctor -> doctor.specialty().equalsIgnoreCase(specialty))
        .toList();

    if (doctors.isEmpty()) {
      return new ApiResponse<>("No doctors found", null, null, true);
    }

    return new ApiResponse<>("Doctors found", null, doctors, true);
  }

  public ApiResponse<List<DoctorPatientDto>> getAvailableDoctors(String specialty, LocalDate date) {
    int month = date.getMonthValue();
    int day = date.getDayOfMonth();
    log.info("Fetching available doctors for specialty: " + specialty + " on date: " + date);
    List<DoctorPatientDto> doctors = doctorExample.getDoctors().stream()
        .filter(doctor -> doctor.specialty().equalsIgnoreCase(specialty))
        .filter(doctor -> doctor.mes() == month)
        .filter(doctorPatientDto -> doctorPatientDto.timeSlots().containsKey(day))
        .toList();

    if (doctors.isEmpty()) {
      return new ApiResponse<>("No doctors found", null, null, true);
    }

    return new ApiResponse<>("Doctors found", null, doctors, true);
  }
}
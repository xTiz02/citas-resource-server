package org.prd.resourceserver.web.patientController;

import java.time.LocalDate;
import java.util.List;
import org.prd.resourceserver.persistence.patient.dto.ApiResponse;
import org.prd.resourceserver.persistence.patient.dto.DoctorPatientDto;
import org.prd.resourceserver.service.implPatient.DoctorPatientImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctors")
public class DoctorPatientController {

  @Autowired
  private DoctorPatientImpl doctorPatientImpl;

  @GetMapping("/{doctorId}/availability")
  public ApiResponse<DoctorPatientDto> getDoctorAvailability(@PathVariable Long doctorId, @RequestParam int mes) {
    try {
      return doctorPatientImpl.getDoctorAvailability(doctorId, mes);
    } catch (Exception e) {
      return new ApiResponse<>(
          "Error al obtener los doctores: " + e.getMessage(),
          null,
          null,
          false
      );
    }
  }

  @GetMapping("/specialty")
  public ApiResponse<List<DoctorPatientDto>> getDoctorsBySpecialty(@RequestParam String specialty) {
    try {
      return doctorPatientImpl.getDoctorsBySpecialty(specialty);
    } catch (Exception e) {
      return new ApiResponse<>(
          "Error al obtener los doctores por especialidad: " + e.getMessage(),
          null,
          null,
          false
      );
    }
  }

  @GetMapping("/available-doctors")
  public ApiResponse<List<DoctorPatientDto>> getAvailableDoctors(@RequestParam String specialty, @RequestParam LocalDate date) {
    try {
      return doctorPatientImpl.getAvailableDoctors(specialty,date);
    } catch (Exception e) {
      return new ApiResponse<>(
          "Error al obtener los doctores disponibles: " + e.getMessage(),
          null,
          null,
          false
      );
    }
  }
}
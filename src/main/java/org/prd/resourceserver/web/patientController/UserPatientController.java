package org.prd.resourceserver.web.patientController;

import lombok.experimental.PackagePrivate;
import org.prd.resourceserver.persistence.patient.dto.ApiResponse;
import org.prd.resourceserver.persistence.patient.entity.UserPatient;
import org.prd.resourceserver.service.implPatient.UserPatientImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/patient")
public class UserPatientController {
  @Autowired
  private UserPatientImpl userPatientImpl;

  @GetMapping("/{username}")
  public ApiResponse<UserPatient> getPatientByUsername(@PathVariable String username) {
    try{
      return userPatientImpl.getPatientByUsername(username);
    } catch (Exception e) {
      return new ApiResponse<>(
          "Error al obtener el paciente: " + e.getMessage(),
          null,
          null,
          false
      );
    }
  }

  @PutMapping("/update/{patientId}")
  public ApiResponse<UserPatient> updatePatient(@PathVariable Long patientId,@RequestBody UserPatient userPatient) {
    try {
      return userPatientImpl.updatePatient(userPatient);
    } catch (Exception e) {
      return new ApiResponse<>(
          "Error al actualizar el paciente: " + e.getMessage(),
          null,
          null,
          false
      );
    }
  }

  @PostMapping("/register")
  public ApiResponse<UserPatient> createPatient(@RequestBody UserPatient userPatient) {
    try {
      return userPatientImpl.savePatient(userPatient);
    } catch (Exception e) {
      return new ApiResponse<>(
          "Error al crear el paciente: " + e.getMessage(),
          null,
          null,
          false
      );
    }
  }

  @GetMapping("/change-password/{patientId}/{newPassword}")
  public ApiResponse<UserPatient> updatePatientPassword(@PathVariable Long patientId, @PathVariable String newPassword) {
    try {
      return userPatientImpl.changePassword(patientId, newPassword);
    } catch (Exception e) {
      return new ApiResponse<>(
          "Error al actualizar la contraseña del paciente: " + e.getMessage(),
          null,
          null,
          false
      );
    }
  }


}
package org.prd.resourceserver.web.patientController;

import java.util.List;
import org.prd.resourceserver.persistence.patient.dto.ApiResponse;
import org.prd.resourceserver.persistence.patient.entity.AppointmentPatient;
import org.prd.resourceserver.service.implPatient.AppointmentPatientImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")
public class AppointmentPatientController {

  @Autowired
  private AppointmentPatientImpl appointmentPatient;


  @GetMapping("/history/{username}")
  public ApiResponse<List<AppointmentPatient>> getHistoryAppointmentsByPatientId(@PathVariable String username) {
    try {
      return appointmentPatient.getHistoryAppointmentsByPatientId(username);
    } catch (Exception e) {
      return new ApiResponse<>(
          "Error al obtener el historial de citas: " + e.getMessage(),
          null,
          null,
          false
      );
    }
  }

  @GetMapping("/upcoming/{username}")
  public ApiResponse<List<AppointmentPatient>> getUpcomingAppointmentsByPatientId(@PathVariable String username) {
    try {
      return appointmentPatient.getUpcomingAppointmentsByPatientId(username);
    } catch (Exception e) {
      return new ApiResponse<>(
          "Error al obtener las citas próximas: " + e.getMessage(),
          null,
          null,
          false
      );
    }
  }

  @PostMapping("")
  public ApiResponse<AppointmentPatient> saveAppointment(@RequestBody AppointmentPatient appointment) {
    try {
      return appointmentPatient.saveAppointment(appointment);
    } catch (Exception e) {
      return new ApiResponse<>(
          "Error al guardar la cita: " + e.getMessage(),
          null,
          null,
          false
      );
    }
  }
}

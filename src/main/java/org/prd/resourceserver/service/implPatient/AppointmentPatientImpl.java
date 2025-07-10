package org.prd.resourceserver.service.implPatient;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.prd.resourceserver.persistence.patient.dto.ApiResponse;
import org.prd.resourceserver.persistence.patient.entity.AppointmentPatient;
import org.prd.resourceserver.persistence.patient.entity.FamilyMemberPatient;
import org.prd.resourceserver.persistence.patient.entity.UserPatient;
import org.prd.resourceserver.persistence.patient.repository.AppointmentPatientRepository;
import org.prd.resourceserver.persistence.patient.repository.FamilyMemberRepository;
import org.prd.resourceserver.persistence.patient.repository.UserPatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AppointmentPatientImpl {
  @Autowired
  private AppointmentPatientRepository appointmentPatientRepository;
  @Autowired
  private UserPatientRepository userPatientRepository;
  @Autowired
  private UserPatientImpl userPatientImpl;
  @Autowired
  private FamilyMemberRepository familyMemberRepository;


  public ApiResponse<List<AppointmentPatient>> getHistoryAppointmentsByPatientId(String username) {
    log.info("Fetching appointment history for patient ID: " + username);
    UserPatient patient = userPatientImpl.getPatientByUsername(username).data();
    List<AppointmentPatient> appointments = appointmentPatientRepository.findAllByPatientId(patient.getId());

    if (appointments.isEmpty()) {
      return new ApiResponse<>("No appointments found for this patient", null, null, false);
    }

    return new ApiResponse<>("Appointments found", null, appointments, true);
  }

  public ApiResponse<List<AppointmentPatient>> getUpcomingAppointmentsByPatientId(String username) {
    log.info("Fetching upcoming appointments for patient ID: " + username);
    UserPatient patient = userPatientImpl.getPatientByUsername(username).data();
    List<AppointmentPatient> appointments = appointmentPatientRepository.getUpcomingAppointmentsByPatientId(patient.getId());

    if (appointments.isEmpty()) {
      return new ApiResponse<>("No upcoming appointments found for this patient", null, null, false);
    }

    return new ApiResponse<>("Upcoming appointments found", null, appointments, true);
  }

  public ApiResponse<AppointmentPatient> saveAppointment(AppointmentPatient appointment) {
    log.info("Saving appointment for patient ID: " + appointment.getPatientId());
    FamilyMemberPatient patient = familyMemberRepository.findById(appointment.getPatientId()).orElse(null);
    //con el mes dia y ttime quitar con el doctorId del horario
    Long doctorId = appointment.getDoctorId();
    int month = appointment.getDate().getMonthValue();
    int day = appointment.getDate().getDayOfMonth();
    String time = appointment.getTime();
    AppointmentPatient savedAppointment = appointmentPatientRepository.save(appointment);
    return new ApiResponse<>("Appointment saved successfully", null, savedAppointment, true);
  }

}

package org.prd.resourceserver.service.implPatient;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.prd.resourceserver.persistence.patient.dto.ApiResponse;
import org.prd.resourceserver.persistence.patient.entity.AppointmentPatient;
import org.prd.resourceserver.persistence.patient.entity.UserPatient;
import org.prd.resourceserver.persistence.patient.repository.AppointmentPatientRepository;
import org.prd.resourceserver.persistence.patient.repository.FamilyMemberRepository;
import org.prd.resourceserver.persistence.patient.repository.UserPatientRepository;
import org.prd.resourceserver.util.AppointmentStatus;
import org.prd.resourceserver.util.DoctorExample;
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
  @Autowired
  private DoctorExample doctorExample;


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
    List<AppointmentStatus> statuses = List.of(AppointmentStatus.pendiente, AppointmentStatus.programada, AppointmentStatus.reprogramada);
    List<AppointmentPatient> appointments = appointmentPatientRepository.getUpcomingAppointmentsByPatientId(patient.getId(),statuses);

    return new ApiResponse<>("Upcoming appointments found", null, appointments, true);
  }

  public ApiResponse<AppointmentPatient> saveAppointment(AppointmentPatient appointment) {
    log.info("Saving appointment for patient ID: " + appointment.getPatientId());
//    FamilyMemberPatient patient = familyMemberRepository.findById(appointment.getPatientId()).orElse(null);
    //con el mes dia y time quitar con el doctorId del horario
    Long doctorId = appointment.getDoctorId();
    int month = appointment.getDate().getMonthValue();
    int day = appointment.getDate().getDayOfMonth();
    String time = appointment.getTime();

    doctorExample.getDoctors().stream().filter(
        doctor -> doctor.id().equals(doctorId) && doctor.mes() == month)
        .findFirst().ifPresent(doctor -> {
          List<String> tiempos = doctor.timeSlots().get(day);
          if (tiempos == null || !tiempos.contains(time)) {
            throw new RuntimeException("The selected time slot is not available for the doctor.");
          }
          List<String> tiempos2 = new ArrayList<>(tiempos); // ← ahora es mutable
          tiempos2.remove(time);
          boolean dayIsAvailable = !tiempos.isEmpty();
          doctorExample.updateSlot(doctorId, tiempos2,month, day,dayIsAvailable);
    });
    appointment.setStatus(AppointmentStatus.pendiente);
    AppointmentPatient savedAppointment = appointmentPatientRepository.save(appointment);
    return new ApiResponse<>("Appointment saved successfully", null, savedAppointment, true);
  }

  public ApiResponse<AppointmentPatient> cancelAppointment2(Long id) {
    log.info("Cancelling appointment with ID: " + id);
    AppointmentPatient appointment = appointmentPatientRepository.findById(id).orElse(null);
    if (appointment == null) {
      return new ApiResponse<>("Appointment not found", null, null, false);
    }
    appointment.setStatus(AppointmentStatus.cancelada);
    AppointmentPatient updatedAppointment = appointmentPatientRepository.save(appointment);
    doctorExample.getDoctors().stream()
        .filter(doctor -> doctor.id().equals(appointment.getDoctorId()))
        .findFirst().ifPresent(doctor -> {
          int month = appointment.getDate().getMonthValue();
          int day = appointment.getDate().getDayOfMonth();
          String time = appointment.getTime();
          List<String> tiempos = doctor.timeSlots().get(day);
          if (tiempos != null) {
            if( !tiempos.contains(time)) {
              tiempos.add(time);
            }
            doctorExample.updateSlot(doctor.id(), tiempos, month, day, true);
          }
        });
    return new ApiResponse<>("Appointment cancelled successfully", null, updatedAppointment, true);
  }


}
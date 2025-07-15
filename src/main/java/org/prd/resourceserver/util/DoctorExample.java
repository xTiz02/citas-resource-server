package org.prd.resourceserver.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.prd.resourceserver.persistence.patient.dto.DoctorPatientDto;
import org.springframework.stereotype.Component;

@Component
public class DoctorExample {

  private final List<DoctorPatientDto> doctors = new ArrayList<>();

  public DoctorExample() {
    Map<Integer, List<String>> timeSlots = new HashMap<>();
    timeSlots.put(18, List.of("08:30", "11:30", "13:00", "15:30"));
    timeSlots.put(21, List.of("10:00", "11:30", "14:15", "16:00"));
    timeSlots.put(23, List.of("07:45", "09:15", "11:30", "14:15"));
    timeSlots.put(25, List.of("08:00", "10:30", "13:30", "15:00"));
    timeSlots.put(28, List.of("09:30", "11:00", "14:15", "16:30"));

    Map<Integer, List<String>> timeSlots1 = new HashMap<>();
//      timeSlots1.put(1, List.of("08:30", "10:30", "12:30", "14:30"));
//      timeSlots1.put(2, List.of("09:00", "11:00", "13:00", "15:00"));
//      timeSlots1.put(3, List.of("07:45", "09:45", "11:45", "13:45"));
//      timeSlots1.put(4, List.of("08:15", "10:15", "12:15", "14:15"));
//      timeSlots1.put(5, List.of("09:30", "11:30", "13:30", "15:30"));
//      timeSlots1.put(6, List.of("08:00", "10:00", "12:00", "14:00"));
//      timeSlots1.put(7, List.of("09:00", "11:00", "13:00", "15:00"));
//      timeSlots1.put(8, List.of("07:30", "09:30", "11:30", "13:30"));
//      timeSlots1.put(9, List.of("08:45", "10:45", "12:45", "14:45"));
//      timeSlots1.put(10, List.of("09:15", "11:15", "13:15", "15:15"));
//      timeSlots1.put(11, List.of("08:30", "10:30", "12:30", "14:30"));
//      timeSlots1.put(12, List.of("09:00", "11:00", "13:00", "15:00"));
    timeSlots1.put(18, List.of("08:00", "10:00", "12:00", "14:00"));
    timeSlots1.put(21, List.of("09:30", "11:30", "13:30", "15:30"));
    timeSlots1.put(25, List.of("08:45", "10:45", "12:45", "14:45"));

    Map<Integer, List<String>> timeSlots2 = new HashMap<>();
    timeSlots2.put(23, List.of("07:30", "09:00", "10:30", "12:00"));
    timeSlots2.put(28, List.of("09:00", "11:00", "13:00", "15:00"));

    Map<Integer, List<String>> timeSlots4 = new HashMap<>();
    timeSlots4.put(21, List.of("08:15", "10:15", "12:15", "14:15"));
    timeSlots4.put(23, List.of("09:45", "11:45", "13:45", "15:45"));
    timeSlots4.put(28, List.of("07:30", "09:30", "11:30", "13:30"));

    Map<Integer, List<String>> timeSlots5 = new HashMap<>();
    timeSlots5.put(18, List.of("08:00", "10:00", "12:00", "14:00"));
    timeSlots5.put(23, List.of("08:30", "10:30", "12:30", "14:30"));
    timeSlots5.put(28, List.of("09:00", "11:00", "13:00", "15:00"));

    Map<Integer, List<String>> timeSlots6 = new HashMap<>();
    timeSlots6.put(21, List.of("09:00", "11:00", "13:00", "15:00"));
    timeSlots6.put(23, List.of("07:45", "09:45", "11:45", "13:45"));
    timeSlots6.put(25, List.of("08:15", "10:15", "12:15", "14:15"));

    doctors.add(new DoctorPatientDto(
        1L,
        "DR(A) JULIAN FALCON JOSE RAUL",
        "MEDICINA FÍSICA-REHABILITACIÓN",
        7,
        true,
        List.of(18, 21, 23, 25, 28),
        timeSlots
    ));

    doctors.add(new DoctorPatientDto(
        2L,
        "DR(A) ANA TORRES LUNA",
        "CARDIOLOGÍA",
        6,
        true,
        List.of(18, 21, 25),
        timeSlots1
    ));

    doctors.add(new DoctorPatientDto(
        3L,
        "DR(A) LUIS MENDOZA CASTRO",
        "DERMATOLOGÍA",
        5,
        true,
        List.of(23, 28),
        timeSlots2
    ));

    doctors.add(new DoctorPatientDto(
        4L,
        "DR(A) PATRICIA RIVERA VÁSQUEZ",
        "PEDIATRÍA",
        9,
        true,
        List.of(18, 25, 28),
        timeSlots
    ));

    doctors.add(new DoctorPatientDto(
        5L,
        "DR(A) VÍCTOR RAMÍREZ SALAZAR",
        "GINECOLOGÍA",
        10,
        true,
        List.of(21, 23, 28),
        timeSlots4
    ));

    doctors.add(new DoctorPatientDto(
        6L,
        "DR(A) MARTA QUISPE GALVEZ",
        "TRAUMATOLOGÍA",
        8,
        true,
        List.of(18, 23, 28),
        timeSlots5
    ));

    doctors.add(new DoctorPatientDto(
        7L,
        "DR(A) RICARDO TICONA LOPEZ",
        "MEDICINA INTERNA",
        7,
        true,
        List.of(21, 23, 25),
        timeSlots
    ));
  }

  public List<DoctorPatientDto> getDoctors() {
    return doctors;
  }

  public void addDoctor(DoctorPatientDto doctor) {
    doctors.add(doctor);
  }

  public void updateSlot(Long doctorId, List<String> updatedLot, int mes, int dia, boolean dayIsAvailable) {
    for (DoctorPatientDto doctor : doctors) {
      if (doctor.id().equals(doctorId) && doctor.mes() == mes) {
        Map<Integer, List<String>> timeSlots = doctor.timeSlots();
        timeSlots.put(dia, updatedLot); // actualiza o reemplaza los horarios del día
        break;
      }
    }
    if(!dayIsAvailable) {
      //remove the day from available days
      for (DoctorPatientDto doctor : doctors) {
        if (doctor.id().equals(doctorId) && doctor.mes() == mes) {
          List<Integer> availableDays = doctor.availableDays();
          availableDays.removeIf(day -> day == dia); // elimina el día de los días disponibles
          break;
        }
      }
    }
  }

}
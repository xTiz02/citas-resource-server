package org.prd.resourceserver.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.prd.resourceserver.persistence.patient.dto.DoctorPatientDto;
import org.springframework.stereotype.Component;

@Component
public class DoctorExample {

  private List<DoctorPatientDto> doctors = new ArrayList<>();

  public List<DoctorPatientDto> getDoctors() {
    Map<Integer, List<String>> timeSlots = new HashMap<>();
    timeSlots.put(18, List.of("08:30", "11:30", "13:00", "15:30"));
    timeSlots.put(21, List.of("10:00", "11:30", "14:15", "16:00"));
    timeSlots.put(23, List.of("07:45", "09:15", "11:30", "14:15"));
    timeSlots.put(25, List.of("08:00", "10:30", "13:30", "15:00"));
    timeSlots.put(28, List.of("09:30", "11:00", "14:15", "16:30"));


    DoctorPatientDto doctor = new DoctorPatientDto(
        1L,
        "DR(A) JULIAN FALCON JOSE RAUL",
        "MEDICINA FÍSICA-REHABILITACIÓN",
        7,
        true,
        List.of(18, 21, 23, 25, 28),
        timeSlots
    );
    doctors.add(doctor);
    return doctors;
  }

  public void addDoctor(DoctorPatientDto doctor) {
    doctors.add(doctor);
  }
}
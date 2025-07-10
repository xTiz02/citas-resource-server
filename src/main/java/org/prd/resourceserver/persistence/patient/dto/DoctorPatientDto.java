package org.prd.resourceserver.persistence.patient.dto;

import java.util.List;
import java.util.Map;

public record DoctorPatientDto(
     Long id,
     String name,
     String specialty,
     int mes,
     boolean available,
     List<Integer> availableDays,
     Map<Integer, List<String>>timeSlots
) {

}
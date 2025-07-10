package org.prd.resourceserver.persistence.patient.dto;

import java.util.List;
import java.util.Map;

public record DoctorPatinetDto(
     Long id,
     String name,
     String specialty,
     boolean available,
     List<Integer> availableDays,
     Map<Integer, List<String>>timeSlots
) {

}
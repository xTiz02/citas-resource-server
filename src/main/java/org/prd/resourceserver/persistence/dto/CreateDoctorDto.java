package org.prd.resourceserver.persistence.dto;

import java.time.LocalDate;
import java.util.List;
import org.prd.resourceserver.util.GenderEnum;

public record CreateDoctorDto(
    Long id,
    String dni,
    String firstName,
    String lastName,
    String email,
    Long userId,
    GenderEnum gender,
    String phone,
    String licenseNumber,
    LocalDate birthDate,
    List<Long> specialties

) {

}

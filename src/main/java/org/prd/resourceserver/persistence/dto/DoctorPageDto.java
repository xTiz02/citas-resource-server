package org.prd.resourceserver.persistence.dto;

import java.time.LocalDate;
import org.prd.resourceserver.util.GenderEnum;

import java.util.Date;
import java.util.List;

public record DoctorPageDto(
    Long id,
    String dni,
    String name,
    String lastName,
    int age,
    LocalDate birthDate,
    GenderEnum gender,
    String licenseNumber,
    String phone,
    String email,
    Date creationDate,
    Date updateDate,
    boolean enabled,
    List<SpecialtyPageDto> specialties
) {
}
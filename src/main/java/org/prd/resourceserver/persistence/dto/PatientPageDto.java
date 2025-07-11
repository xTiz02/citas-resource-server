package org.prd.resourceserver.persistence.dto;

import java.time.LocalDate;
import org.prd.resourceserver.util.GenderEnum;

import java.util.Date;

public record PatientPageDto(
    Long id,
    String dni,
    String name,
    String lastName,
    int age,
    LocalDate birthDate,
    GenderEnum gender,
    Date lastVisitDate,
    String address,
    String phone,
    String email,
    Date creationDate,
    Date updateDate,
    boolean enabled
) {
}
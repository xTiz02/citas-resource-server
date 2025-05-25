package org.prd.resourceserver.persistence.dto;

import org.prd.resourceserver.util.GenderEnum;

import java.util.Date;

public record PatientPageDto(
    Long id,
    String dni,
    String name,
    String lastName,
    int age,
    Date birthDate,
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
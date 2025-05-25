package org.prd.resourceserver.util.mapper;

import org.prd.resourceserver.persistence.dto.PatientPageDto;
import org.prd.resourceserver.persistence.entity.Patient;
import org.prd.resourceserver.util.UtilConstants;

import java.util.Date;

public class PatientMapper {

    public static PatientPageDto toPageDto(Patient patient, Date lastVisitDate) {
        return new PatientPageDto(
                patient.getId(),
                patient.getDni(),
                patient.getName(),
                patient.getLastName(),
                UtilConstants.getAge(patient.getBirthDate().getYear()),
                patient.getBirthDate(),
                patient.getGender(),
                lastVisitDate,
                patient.getAddress(),
                patient.getPhone(),
                patient.getEmail(),
                patient.getCreationDate(),
                patient.getUpdateDate(),
                patient.isEnabled()
        );
    }
}
package org.prd.resourceserver.util.mapper;

import org.prd.resourceserver.persistence.dto.DoctorPageDto;
import org.prd.resourceserver.persistence.entity.Doctor;
import org.prd.resourceserver.util.UtilConstants;

public class DoctorMapper {

    public static DoctorPageDto toPageDto(Doctor doctor) {
        return new DoctorPageDto(
                doctor.getId(),
                doctor.getDni(),
                doctor.getFirstName(),
                doctor.getLastName(),
                UtilConstants.getAge(doctor.getBirthDate().getYear()),
                doctor.getBirthDate(),
                doctor.getGender(),
                doctor.getLicenseNumber(),
                doctor.getPhone(),
                doctor.getEmail(),
                doctor.getCreationDate(),
                doctor.getUpdateDate(),
                doctor.isEnabled(),
                doctor.getSpecialties().stream().map(SpecialtyMapper::toPageDto).toList()
        );
    }

}
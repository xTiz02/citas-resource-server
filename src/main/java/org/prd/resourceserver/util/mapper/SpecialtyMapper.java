package org.prd.resourceserver.util.mapper;

import org.prd.resourceserver.persistence.dto.SpecialtyPageDto;
import org.prd.resourceserver.persistence.entity.Specialty;

public class SpecialtyMapper {
    public static SpecialtyPageDto toPageDto(Specialty specialty) {
        return  new SpecialtyPageDto(
                specialty.getId(),
                specialty.getName(),
                specialty.getCreatedAt(),
                specialty.getUpdatedAt(),
                specialty.isEnabled()
        );
    }


}
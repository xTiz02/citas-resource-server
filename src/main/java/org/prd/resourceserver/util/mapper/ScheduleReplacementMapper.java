package org.prd.resourceserver.util.mapper;

import org.prd.resourceserver.persistence.dto.CreateReplacementDto;
import org.prd.resourceserver.persistence.dto.ScheduleReplacementPageDto;
import org.prd.resourceserver.persistence.entity.ScheduleReplacement;

public class ScheduleReplacementMapper {

  public static ScheduleReplacementPageDto toDto(ScheduleReplacement replacement){
    return new ScheduleReplacementPageDto(
        replacement.getId(),
        DoctorMapper.toPageDto(replacement.getAbsentDoctor()),
        DoctorMapper.toPageDto(replacement.getCoveringDoctor()),
        replacement.getDateFrom(),
        replacement.getDateTo(),
        replacement.getCreatedAt(),
        replacement.getUpdatedAt(),
        ScheduleMapper.toPageDto(replacement.getSchedule())
    );
  }

  public static ScheduleReplacement createToEntity(CreateReplacementDto replacementDto) {
    return ScheduleReplacement.builder()
        .dateFrom(replacementDto.dateFrom())
        .dateTo(replacementDto.dateTo())
        .build();
  }
}
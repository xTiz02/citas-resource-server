package org.prd.resourceserver.util.mapper;

import org.prd.resourceserver.persistence.dto.ScheduleExceptionPageDto;
import org.prd.resourceserver.persistence.entity.ScheduleException;

public class ScheduleExceptionMapper {

    public static ScheduleExceptionPageDto toPageDto(ScheduleException scheduleException) {
        return new ScheduleExceptionPageDto(
                scheduleException.getId(),
                scheduleException.getDateException(),
                scheduleException.getTurn(),
                DoctorMapper.toPageDto(scheduleException.getDoctor()),
                scheduleException.getSchedulesAfected().stream().map(ScheduleMapper::toPageLiteDto).toList(),
                scheduleException.getCreatedAt(),
                scheduleException.getUpdatedAt()
        );
    }
}
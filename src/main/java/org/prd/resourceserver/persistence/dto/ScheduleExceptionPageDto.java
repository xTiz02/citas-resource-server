package org.prd.resourceserver.persistence.dto;

import java.time.LocalDate;
import java.util.List;
import org.prd.resourceserver.util.DayWeekEnum;
import org.prd.resourceserver.util.TurnEnum;

import java.util.Date;

public record ScheduleExceptionPageDto(
        Long id,
        LocalDate dateException,
        TurnEnum turn,
        DoctorPageDto doctor,
        List<SchedulePageLiteDto> schedule,
        Date createdAt,
        Date updatedAt
) {
    public record SchedulePageLiteDto(
            Long id,
            LocalDate startDate,
            LocalDate endDate,
            DayWeekEnum dayOfTheWeek,
            TurnEnum turn,
            String startTime,
            String endTime,
            int duration,
            boolean enabled,
            DoctorPageDto doctor,
            SpecialtyPageDto specialty,
            LocationPageDto ubication,
            Date createdAt,
            Date updatedAt
    ) {
    }
}
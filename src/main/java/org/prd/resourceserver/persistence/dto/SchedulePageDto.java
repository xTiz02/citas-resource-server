package org.prd.resourceserver.persistence.dto;

import java.time.LocalDate;
import org.prd.resourceserver.util.DayWeekEnum;
import org.prd.resourceserver.util.TurnEnum;

import java.util.Date;
import java.util.List;

public record SchedulePageDto(
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
        LocationPageDto location,
        Date createdAt,
        Date updatedAt,
        List<ScheduleExceptionPageDto> scheduleExceptionList
) {
}
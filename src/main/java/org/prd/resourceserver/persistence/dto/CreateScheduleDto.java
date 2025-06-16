package org.prd.resourceserver.persistence.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import org.prd.resourceserver.util.DayWeekEnum;
import org.prd.resourceserver.util.TurnEnum;
import org.springframework.dao.support.DaoSupport;

import java.time.DayOfWeek;
import java.util.Date;

public record CreateScheduleDto(
        @NotNull(message = "Start date is required")
        LocalDate startDate,
        @NotNull(message = "End date is required")
        LocalDate endDate,
        @NotNull(message = "End time is required")
        String endTime,
        @NotNull(message = "Start time is required")
        String startTime,
        @NotNull(message = "Day of the week is required")
        DayWeekEnum dayOfTheWeek,
        @NotNull(message = "Turn is required")
        TurnEnum turn,
        @NotNull(message = "Start time is required")
        int duration,
        @NotNull(message = "End time is required")
        Long doctorId,
        @NotNull(message = "Specialty ID is required")
        Long specialtyId,
        @NotNull(message = "Ubication ID is required")
        Long locationId

) {
}
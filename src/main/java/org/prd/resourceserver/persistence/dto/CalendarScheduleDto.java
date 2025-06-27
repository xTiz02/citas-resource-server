package org.prd.resourceserver.persistence.dto;

import java.time.LocalDate;
import java.util.List;
import org.prd.resourceserver.util.DayWeekEnum;
import org.prd.resourceserver.util.GenderEnum;
import org.prd.resourceserver.util.TurnEnum;

public record CalendarScheduleDto(
    LocalDate date,
    List<Schedule> schedules
) {
  public record Schedule(
      Long doctorScheduleId,
      DayWeekEnum dayOfTheWeek,
      TurnEnum turn,
      LocationCalendar location,
      SpecialtyCalendar mainSpecialty,
      DoctorCalendar doctor,
      List<TimeSlot> slots,
      int order
  ) {
    public record LocationCalendar(
        Long id,
        String name,
        String address
    ){ }

    public record DoctorCalendar(
        Long id,
        String firstName,
        String lastName,
        String dni,
        String phone,
        GenderEnum gender,
        List<SpecialtyCalendar> specialties
    ){ }

    public record SpecialtyCalendar(
        Long id,
        String name
    ){ }

    public record TimeSlot(
        int duration,
        String startTime,
        String endTime,
        int order
    ) { }
  }

}

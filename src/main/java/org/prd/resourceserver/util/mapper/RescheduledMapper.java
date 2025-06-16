package org.prd.resourceserver.util.mapper;

import org.prd.resourceserver.persistence.dto.RescheduledDto;
import org.prd.resourceserver.persistence.entity.RescheduledAppointment;

public class RescheduledMapper {

  public static RescheduledDto toDto(RescheduledAppointment rescheduledAppointment) {
    return new RescheduledDto(
        rescheduledAppointment.getId(),
        rescheduledAppointment.getNewDate(),
        rescheduledAppointment.getStartTime(),
        rescheduledAppointment.getDuration(),
        ScheduleMapper.toPageDto(rescheduledAppointment.getSchedule()),
        rescheduledAppointment.getOrderNumber(),
        rescheduledAppointment.getCreatedAt()
    );
  }
}
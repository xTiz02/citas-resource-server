package org.prd.resourceserver.util.mapper;

import org.prd.resourceserver.persistence.dto.CreateScheduleDto;
import org.prd.resourceserver.persistence.dto.ScheduleExceptionPageDto;
import org.prd.resourceserver.persistence.dto.SchedulePageDto;
import org.prd.resourceserver.persistence.entity.DoctorSchedule;

import java.util.List;

public class ScheduleMapper {

    public static DoctorSchedule toEntity(CreateScheduleDto createScheduleDto) {
        return DoctorSchedule.builder()
                .dayOfTheWeek(createScheduleDto.dayOfTheWeek())
                .endDate(createScheduleDto.endDate())
                .startDate(createScheduleDto.startDate())
                .duration(createScheduleDto.duration())
                .endTime(createScheduleDto.endTime())
                .startTime(createScheduleDto.startTime())
                .turn(createScheduleDto.turn())
                .build();
    }

    public static DoctorSchedule toEntity(DoctorSchedule existingSchedule, CreateScheduleDto createScheduleDto) {
        existingSchedule.setDayOfTheWeek(createScheduleDto.dayOfTheWeek());
        existingSchedule.setEndDate(createScheduleDto.endDate());
        existingSchedule.setStartDate(createScheduleDto.startDate());
        existingSchedule.setDuration(createScheduleDto.duration());
        existingSchedule.setEndTime(createScheduleDto.endTime());
        existingSchedule.setStartTime(createScheduleDto.startTime());
        existingSchedule.setTurn(createScheduleDto.turn());
        return existingSchedule;
    }

    public static SchedulePageDto toPageDto(DoctorSchedule doctorSchedule) {

        return new SchedulePageDto(
                doctorSchedule.getId(),
                doctorSchedule.getStartDate(),
                doctorSchedule.getEndDate(),
                doctorSchedule.getDayOfTheWeek(),
                doctorSchedule.getTurn(),
                doctorSchedule.getStartTime(),
                doctorSchedule.getEndTime(),
                doctorSchedule.getDuration(),
                doctorSchedule.isEnabled(),
                DoctorMapper.toPageDto(doctorSchedule.getDoctor()),
                SpecialtyMapper.toPageDto(doctorSchedule.getSpecialty()),
                LocationMapper.toPageDto(doctorSchedule.getLocation()),
                doctorSchedule.getCreatedAt(),
                doctorSchedule.getUpdatedAt(),
                !doctorSchedule.getExceptions().isEmpty() ?
                        doctorSchedule.getExceptions().stream()
                                .map(ScheduleExceptionMapper::toPageDto)
                                .toList() :
                        List.of()
        );
    }

    public static ScheduleExceptionPageDto.SchedulePageLiteDto toPageLiteDto(DoctorSchedule doctorSchedule) {
        return new ScheduleExceptionPageDto.SchedulePageLiteDto(
                doctorSchedule.getId(),
                doctorSchedule.getStartDate(),
                doctorSchedule.getEndDate(),
                doctorSchedule.getDayOfTheWeek(),
                doctorSchedule.getTurn(),
                doctorSchedule.getStartTime(),
                doctorSchedule.getEndTime(),
                doctorSchedule.getDuration(),
                doctorSchedule.isEnabled(),
                DoctorMapper.toPageDto(doctorSchedule.getDoctor()),
                SpecialtyMapper.toPageDto(doctorSchedule.getSpecialty()),
                LocationMapper.toPageDto(doctorSchedule.getLocation()),
                doctorSchedule.getCreatedAt(),
                doctorSchedule.getUpdatedAt()
        );
    }
}
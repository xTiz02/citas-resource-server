package org.prd.resourceserver.web.controller;

import jakarta.validation.Valid;
import org.prd.resourceserver.persistence.dto.CreateScheduleDto;
import org.prd.resourceserver.persistence.dto.PageResponse;
import org.prd.resourceserver.persistence.dto.SchedulePageDto;
import org.prd.resourceserver.persistence.dto.UserPageDto;
import org.prd.resourceserver.persistence.entity.DoctorSchedule;
import org.prd.resourceserver.service.ScheduleService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/doctor/{id}")
    public ResponseEntity<PageResponse<SchedulePageDto>> getAllSchedulesByDoctor(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(scheduleService.getAllSchedulesByDoctor(id,pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<SchedulePageDto> createSchedule(@RequestBody @Valid CreateScheduleDto createScheduleDto) {
        return ResponseEntity.ok(scheduleService.createSchedule(createScheduleDto));
    }

    @PostMapping("/validate")
    public ResponseEntity<List<SchedulePageDto>> validateSchedule(@RequestParam(required = false) Long id, @RequestBody @Valid CreateScheduleDto createScheduleDto) {

        return ResponseEntity.ok(scheduleService.validateSchedule(id,createScheduleDto));
    }




}
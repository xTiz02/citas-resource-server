package org.prd.resourceserver.web.controller;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import org.prd.resourceserver.persistence.dto.CreateReplacementDto;
import org.prd.resourceserver.persistence.dto.DoctorPageDto;
import org.prd.resourceserver.persistence.dto.PageResponse;
import org.prd.resourceserver.persistence.dto.ScheduleReplacementPageDto;
import org.prd.resourceserver.service.ReplacementService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/replacement")
public class ReplacementController {

  private final ReplacementService replacementService;

  public ReplacementController(ReplacementService replacementService) {
    this.replacementService = replacementService;
  }

  @GetMapping("/all/{scheduleId}")
  public ResponseEntity<PageResponse<ScheduleReplacementPageDto>> getAllReplacements(Pageable pageable,@PathVariable long scheduleId) {
    return ResponseEntity.ok(replacementService.findAllBySchedule(
        pageable, scheduleId
    ));
  }

  @PostMapping("/create/{scheduleId}")
  public ResponseEntity<ScheduleReplacementPageDto> createReplacement(@RequestBody @Valid CreateReplacementDto replacement, @PathVariable long scheduleId) {
    return ResponseEntity.ok(replacementService.createReplacement(scheduleId,replacement));
  }

  @PostMapping("/update/{scheduleId}")
  public ResponseEntity<ScheduleReplacementPageDto> updateReplacement(@RequestBody @Valid CreateReplacementDto replacement, @PathVariable long scheduleId) {
    return ResponseEntity.ok(replacementService.updateReplacement(scheduleId,replacement));
  }


  @DeleteMapping("/delete/{replacementId}")
  public ResponseEntity<Void> deleteReplacement(@PathVariable long replacementId) {
    replacementService.deleteReplacement(replacementId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/covering-doctors/{scheduleId}")
  public ResponseEntity<List<DoctorPageDto>> findAllCoveringDoctors(
      @PathVariable long scheduleId,
      @RequestParam LocalDate dateFrom,
      @RequestParam LocalDate dateTo) {
    return ResponseEntity.ok(replacementService.findAllCoveringDoctors(scheduleId, dateFrom, dateTo));
  }
}
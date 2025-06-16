package org.prd.resourceserver.service;

import java.util.List;
import org.prd.resourceserver.persistence.dto.CreateReplacementDto;
import org.prd.resourceserver.persistence.dto.PageResponse;
import org.prd.resourceserver.persistence.dto.ScheduleReplacementPageDto;
import org.prd.resourceserver.persistence.entity.ScheduleReplacement;
import org.springframework.data.domain.Pageable;

public interface ReplacementService {

  PageResponse<ScheduleReplacementPageDto> findAllBySchedule(Pageable pageable, long scheduleId);
  ScheduleReplacementPageDto createReplacement(long scheduleId,CreateReplacementDto replacementDto);
  ScheduleReplacementPageDto updateReplacement(long replacementId, CreateReplacementDto replacementDto);
  void deleteReplacement(long replacementId);
}
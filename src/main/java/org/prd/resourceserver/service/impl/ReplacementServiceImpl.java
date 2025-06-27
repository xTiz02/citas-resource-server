package org.prd.resourceserver.service.impl;

import org.prd.resourceserver.persistence.dto.CreateReplacementDto;
import org.prd.resourceserver.persistence.dto.PageResponse;
import org.prd.resourceserver.persistence.dto.ScheduleReplacementPageDto;
import org.prd.resourceserver.persistence.entity.Doctor;
import org.prd.resourceserver.persistence.entity.DoctorSchedule;
import org.prd.resourceserver.persistence.entity.ScheduleReplacement;
import org.prd.resourceserver.persistence.repository.ReplacementRepository;
import org.prd.resourceserver.persistence.repository.ScheduleRepository;
import org.prd.resourceserver.service.ReplacementService;
import org.prd.resourceserver.util.UtilConstants;
import org.prd.resourceserver.util.mapper.ScheduleReplacementMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReplacementServiceImpl implements ReplacementService
{

  private final ReplacementRepository replacementRepository;
  private final ScheduleRepository scheduleRepository;

  public ReplacementServiceImpl(ReplacementRepository replacementRepository,
      ScheduleRepository scheduleRepository) {
    this.replacementRepository = replacementRepository;
    this.scheduleRepository = scheduleRepository;
  }

  @Override
  public PageResponse<ScheduleReplacementPageDto> findAllBySchedule(Pageable pageable, long scheduleId){
    var page = replacementRepository.findPageBySchedule_Id(pageable,scheduleId);
    return new PageResponse<>(
        page.getContent().stream()
            .map(ScheduleReplacementMapper::toDto)
            .toList(),
        page.getNumber(),
        page.getSize(),
        page.getTotalElements(),
        page.getTotalPages(),
        page.isFirst(),
        page.isLast()
    );
  }

  @Override
  public ScheduleReplacementPageDto createReplacement(long scheduleId,
      CreateReplacementDto replacementDto) {

    DoctorSchedule schedule = scheduleRepository.findById(scheduleId)
        .orElseThrow(() -> new IllegalArgumentException("Schedule not found with id: " + scheduleId));

    validateReplacement(schedule, replacementDto);

    var replacement = ScheduleReplacementMapper.createToEntity(replacementDto);
    replacement.setSchedule(new DoctorSchedule(schedule.getId()));
    replacement.setAbsentDoctor(new Doctor(replacementDto.absentDoctorId()));
    replacement.setCoveringDoctor(new Doctor(replacementDto.coveringDoctor()));

    return ScheduleReplacementMapper.toDto(
        replacementRepository.save(replacement));
  }

  @Override
  public ScheduleReplacementPageDto updateReplacement(long scheduleId,
      CreateReplacementDto replacementDto) {

    DoctorSchedule schedule = scheduleRepository.findById(scheduleId)
        .orElseThrow(() -> new IllegalArgumentException("Schedule not found with id: " + scheduleId));
    ScheduleReplacement existingReplacement = replacementRepository.findById(replacementDto.replacementId())
        .orElseThrow(() -> new IllegalArgumentException("Replacement not found with id: " + replacementDto.replacementId()));

    validateReplacement(schedule, replacementDto);

    existingReplacement.setDateFrom(replacementDto.dateFrom());
    existingReplacement.setDateTo(replacementDto.dateTo());
    existingReplacement.setAbsentDoctor(new Doctor(replacementDto.absentDoctorId()));
    existingReplacement.setCoveringDoctor(new Doctor(replacementDto.coveringDoctor()));
    return ScheduleReplacementMapper.toDto(
        replacementRepository.save(existingReplacement));
  }

  @Override
  public void deleteReplacement(long replacementId) {
    replacementRepository.deleteById(replacementId);
  }


  public void validateReplacement(DoctorSchedule schedule,CreateReplacementDto replacementDto) {

    if(replacementDto.dateFrom().isAfter(replacementDto.dateTo())){
      throw new IllegalArgumentException("Date 'from' cannot be after date 'to'.");
    }

    if(!(replacementDto.dateFrom().isAfter(schedule.getStartDate()) ||
        replacementDto.dateTo().isBefore(schedule.getEndDate()))){
      throw new IllegalArgumentException("Replacement dates must be within the schedule's date range.");
    }

    if(schedule.isHasReplacement()){
      schedule.getReplacements().stream()
          .filter(replacement -> UtilConstants.isDateRangeOverlapping(
              replacement.getDateFrom(),replacement.getDateTo(), replacementDto.dateFrom(), replacementDto.dateTo()))
          .filter(replacement -> !replacement.getId().equals(replacementDto.replacementId()))
          .findFirst()
          .ifPresent(replacement -> {
            throw new IllegalArgumentException("Replacement already exists for this schedule in the specified date range.");
          });
    }
  }
}
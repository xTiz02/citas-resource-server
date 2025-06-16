package org.prd.resourceserver.service;

import org.prd.resourceserver.persistence.dto.CreateSpecialtyDto;
import org.prd.resourceserver.persistence.dto.PageResponse;
import org.prd.resourceserver.persistence.dto.SpecialtyPageDto;
import org.prd.resourceserver.persistence.dto.UserDetailsDto;
import org.prd.resourceserver.persistence.dto.UserPageDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SpecialtyService {
    PageResponse<SpecialtyPageDto> getAllSpecialties(Pageable pageable);
    List<SpecialtyPageDto> getAllSpecialtiesByEnabledAndDoctor(Long doctorId,boolean enabled);
    SpecialtyPageDto getSpecialtyById(Long id);
    SpecialtyPageDto createSpecialty(CreateSpecialtyDto createSpecialtyDto);
    SpecialtyPageDto updateSpecialty(CreateSpecialtyDto updateSpecialtyDto);
    void deleteSpecialtyById(Long id);
}
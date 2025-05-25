package org.prd.resourceserver.service;

import org.prd.resourceserver.persistence.dto.PageResponse;
import org.prd.resourceserver.persistence.dto.SpecialtyPageDto;
import org.prd.resourceserver.persistence.dto.UserDetailsDto;
import org.prd.resourceserver.persistence.dto.UserPageDto;
import org.springframework.data.domain.Pageable;

public interface SpecialtyService {
    PageResponse<SpecialtyPageDto> getAllSpecialties(Pageable pageable);
}
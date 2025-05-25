package org.prd.resourceserver.service;

import org.prd.resourceserver.persistence.dto.PageResponse;
import org.prd.resourceserver.persistence.dto.PatientPageDto;
import org.prd.resourceserver.persistence.dto.SpecialtyPageDto;
import org.springframework.data.domain.Pageable;

public interface PatientService {
    PageResponse<PatientPageDto> getAllPatients(Pageable pageable);
}
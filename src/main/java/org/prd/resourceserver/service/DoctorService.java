package org.prd.resourceserver.service;

import org.prd.resourceserver.persistence.dto.DoctorPageDto;
import org.prd.resourceserver.persistence.dto.PageResponse;
import org.prd.resourceserver.persistence.dto.PatientPageDto;
import org.springframework.data.domain.Pageable;

public interface DoctorService {
    PageResponse<DoctorPageDto> getAllDoctors(Pageable pageable);
}
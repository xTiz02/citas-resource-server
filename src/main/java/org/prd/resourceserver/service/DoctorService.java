package org.prd.resourceserver.service;

import org.hibernate.sql.Update;
import org.prd.resourceserver.persistence.dto.CreateDoctorDto;
import org.prd.resourceserver.persistence.dto.DoctorPageDto;
import org.prd.resourceserver.persistence.dto.PageResponse;
import org.prd.resourceserver.persistence.dto.PatientPageDto;
import org.springframework.data.domain.Pageable;

public interface DoctorService {
    PageResponse<DoctorPageDto> getAllDoctors(Pageable pageable);
    DoctorPageDto getDoctorById(Long id);
    DoctorPageDto createDoctor(CreateDoctorDto createDoctorDto);
    DoctorPageDto updateDoctor(CreateDoctorDto updateDoctorDto);
    void deleteDoctorById(Long id);
}
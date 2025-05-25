package org.prd.resourceserver.service.impl;

import org.prd.resourceserver.persistence.dto.DoctorPageDto;
import org.prd.resourceserver.persistence.dto.PageResponse;
import org.prd.resourceserver.persistence.entity.Doctor;
import org.prd.resourceserver.persistence.repository.DoctorRepository;
import org.prd.resourceserver.service.DoctorService;
import org.prd.resourceserver.util.mapper.DoctorMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public PageResponse<DoctorPageDto> getAllDoctors(Pageable pageable) {
        Page<Doctor> page = doctorRepository.findAll(pageable);

        return new PageResponse<>(
                page.getContent().stream().map(DoctorMapper::toPageDto).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.isLast(),
                page.isFirst()
        );
    }
}
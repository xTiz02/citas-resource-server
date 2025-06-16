package org.prd.resourceserver.service.impl;

import org.prd.resourceserver.persistence.dto.PageResponse;
import org.prd.resourceserver.persistence.dto.SpecialtyPageDto;
import org.prd.resourceserver.persistence.entity.Doctor;
import org.prd.resourceserver.persistence.repository.DoctorRepository;
import org.prd.resourceserver.persistence.repository.SpecialtyRepository;
import org.prd.resourceserver.service.SpecialtyService;
import org.prd.resourceserver.util.mapper.SpecialtyMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialtyServiceImpl implements SpecialtyService {

    private final SpecialtyRepository specialtyRepository;
    private final DoctorRepository doctorRepository;

    public SpecialtyServiceImpl(SpecialtyRepository specialtyRepository, DoctorRepository doctorRepository) {
        this.specialtyRepository = specialtyRepository;
        this.doctorRepository = doctorRepository;
    }
    @Override
    public PageResponse<SpecialtyPageDto> getAllSpecialties(Pageable pageable) {
        var page = specialtyRepository.findAllSpecialties(pageable);
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast(),
                page.isFirst()
        );
    }

    @Override
    public List<SpecialtyPageDto> getAllSpecialtiesByEnabledAndDoctor(Long doctorId, boolean enabled) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with id: " + doctorId));
        return doctor.getSpecialties().stream()
                .filter(specialty -> specialty.isEnabled() == enabled)
                .map(SpecialtyMapper::toPageDto)
                .toList();
    }
}
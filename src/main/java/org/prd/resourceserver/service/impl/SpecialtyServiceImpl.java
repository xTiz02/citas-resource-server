package org.prd.resourceserver.service.impl;

import org.prd.resourceserver.persistence.dto.PageResponse;
import org.prd.resourceserver.persistence.dto.SpecialtyPageDto;
import org.prd.resourceserver.persistence.repository.SpecialtyRepository;
import org.prd.resourceserver.service.SpecialtyService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SpecialtyServiceImpl implements SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    public SpecialtyServiceImpl(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }
    @Override
    public PageResponse<SpecialtyPageDto> getAllSpecialties(Pageable pageable) {
        var page = specialtyRepository.findAllSpecialties(pageable);
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.isLast(),
                page.isFirst()
        );
    }
}
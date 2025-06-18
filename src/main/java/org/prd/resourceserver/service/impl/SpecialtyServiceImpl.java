package org.prd.resourceserver.service.impl;

import java.util.HashSet;
import java.util.Set;
import org.prd.resourceserver.persistence.dto.CreateSpecialtyDto;
import org.prd.resourceserver.persistence.dto.PageResponse;
import org.prd.resourceserver.persistence.dto.SpecialtyPageDto;
import org.prd.resourceserver.persistence.entity.Doctor;
import org.prd.resourceserver.persistence.entity.Location;
import org.prd.resourceserver.persistence.entity.Specialty;
import org.prd.resourceserver.persistence.repository.DoctorRepository;
import org.prd.resourceserver.persistence.repository.LocationRepository;
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
    private final LocationRepository locationRepository;

    public SpecialtyServiceImpl(SpecialtyRepository specialtyRepository, DoctorRepository doctorRepository,
        LocationRepository locationRepository) {
        this.specialtyRepository = specialtyRepository;
        this.doctorRepository = doctorRepository;
      this.locationRepository = locationRepository;
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

    @Override
    public SpecialtyPageDto getSpecialtyById(Long id) {
        return specialtyRepository.findById(id)
                .map(SpecialtyMapper::toPageDto)
                .orElseThrow(() -> new RuntimeException("Specialty not found with id: " + id));
    }

    @Override
    public SpecialtyPageDto createSpecialty(CreateSpecialtyDto createSpecialtyDto) {
        Specialty specialty = new Specialty();
        specialty.setName(createSpecialtyDto.name());
        specialty.setEnabled(true); // Default to enabled
        Set<Location> locations = new HashSet<>();
        for(long i : createSpecialtyDto.locationIds()){
            Location location = locationRepository.findById(i)
                    .orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + i));
            locations.add(location);
        }

        specialty.setLocations(locations);


        return SpecialtyMapper.toPageDto(specialtyRepository.save(specialty));
    }

    @Override
    public SpecialtyPageDto updateSpecialty(CreateSpecialtyDto updateSpecialtyDto) {
        Specialty specialty = specialtyRepository.findById(updateSpecialtyDto.id())
                .orElseThrow(() -> new RuntimeException("Specialty not found with id: " + updateSpecialtyDto.id()));
        specialty.setName(updateSpecialtyDto.name());
        Set<Location> locations = new HashSet<>();
        for (long i : updateSpecialtyDto.locationIds()) {
            Location location = locationRepository.findById(i)
                    .orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + i));
            locations.add(location);
        }
        specialty.getLocations().clear();
        specialty.setLocations(locations);

        return SpecialtyMapper.toPageDto(specialtyRepository.save(specialty));
    }

    @Override
    public void deleteSpecialtyById(Long id) {
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Specialty not found with id: " + id));
        specialty.setEnabled(false);
        specialtyRepository.save(specialty);
    }
}
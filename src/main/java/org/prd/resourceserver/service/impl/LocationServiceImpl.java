package org.prd.resourceserver.service.impl;

import org.prd.resourceserver.persistence.dto.CreateLocationDto;
import org.prd.resourceserver.persistence.dto.LocationPageDto;
import org.prd.resourceserver.persistence.entity.Specialty;
import org.prd.resourceserver.persistence.repository.SpecialtyRepository;
import org.prd.resourceserver.service.LocationService;
import org.prd.resourceserver.util.mapper.LocationMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private final SpecialtyRepository specialtyRepository;

    public LocationServiceImpl(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    @Override
    public List<LocationPageDto> findAllLocationsBySpecialty(Long specialtyId, boolean enabled) {
        Specialty specialty = specialtyRepository.findById(specialtyId)
                .orElseThrow(() -> new IllegalArgumentException("Specialty not found with id: " + specialtyId));
        return specialty.getLocations().stream()
                .filter(location -> location.isEnabled() == enabled)
                .map(LocationMapper::toPageDto)
                .toList();
    }

    @Override
    public LocationPageDto findLocationById(Long id) {
        return null;
    }

    @Override
    public LocationPageDto createLocation(CreateLocationDto createLocationDto) {
        return null;
    }

    @Override
    public LocationPageDto updateLocation(CreateLocationDto createLocationDto) {
        return null;
    }

    @Override
    public void deleteLocation(Long id) {

    }
}
package org.prd.resourceserver.service.impl;

import org.prd.resourceserver.persistence.dto.CreateLocationDto;
import org.prd.resourceserver.persistence.dto.LocationPageDto;
import org.prd.resourceserver.persistence.entity.Location;
import org.prd.resourceserver.persistence.entity.Specialty;
import org.prd.resourceserver.persistence.repository.LocationRepository;
import org.prd.resourceserver.persistence.repository.SpecialtyRepository;
import org.prd.resourceserver.service.LocationService;
import org.prd.resourceserver.util.mapper.LocationMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private final SpecialtyRepository specialtyRepository;
    private final LocationRepository locationRepository;

    public LocationServiceImpl(SpecialtyRepository specialtyRepository,
        LocationRepository locationRepository) {
        this.specialtyRepository = specialtyRepository;
      this.locationRepository = locationRepository;
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
    public List<LocationPageDto> findAllLocations() {
        return locationRepository.findAll().stream()
            .filter(Location::isEnabled)
                .map(LocationMapper::toPageDto)
                .toList();
    }

    @Override
    public LocationPageDto findLocationById(Long id) {
        return locationRepository.findById(id)
                .map(LocationMapper::toPageDto)
                .orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + id));
    }

    @Override
    public LocationPageDto createLocation(CreateLocationDto createLocationDto) {
        Location location = new Location();
        location.setName(createLocationDto.name());
        location.setAddress(createLocationDto.address());
        location.setEnabled(true);
        return LocationMapper.toPageDto(locationRepository.save(location));
    }

    @Override
    public LocationPageDto updateLocation(CreateLocationDto createLocationDto) {
        Location location = locationRepository.findById(createLocationDto.id())
                .orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + createLocationDto.id()));
        location.setName(createLocationDto.name());
        location.setAddress(createLocationDto.address());
        return LocationMapper.toPageDto(locationRepository.save(location));
    }

    @Override
    public void deleteLocation(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Location not found with id: " + id));
        location.setEnabled(false);
        locationRepository.save(location);
    }
}
package org.prd.resourceserver.service;

import org.prd.resourceserver.persistence.dto.CreateLocationDto;
import org.prd.resourceserver.persistence.dto.LocationPageDto;

import java.util.List;

public interface LocationService {
    List<LocationPageDto> findAllLocationsBySpecialty(Long specialtyId, boolean enabled);
    List<LocationPageDto> findAllLocations();
    LocationPageDto findLocationById(Long id);
    LocationPageDto createLocation(CreateLocationDto createLocationDto);
    LocationPageDto updateLocation(CreateLocationDto createLocationDto);
    void deleteLocation(Long id);
}
package org.prd.resourceserver.service;

import org.prd.resourceserver.persistence.dto.LocationPageDto;

import java.util.List;

public interface LocationService {
    List<LocationPageDto> findAllLocationsBySpecialty(Long specialtyId, boolean enabled);
}
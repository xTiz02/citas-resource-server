package org.prd.resourceserver.web.controller;

import org.prd.resourceserver.persistence.dto.LocationPageDto;
import org.prd.resourceserver.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/all/{specialtyId}")
    public ResponseEntity<List<LocationPageDto>> getAllLocationsBySpecialty(@PathVariable Long specialtyId, boolean enabled) {
        return ResponseEntity.ok(locationService.findAllLocationsBySpecialty(specialtyId, enabled));
    }

    @GetMapping("/all")
    public ResponseEntity<List<LocationPageDto>> getAllLocations() {
        return ResponseEntity.ok(locationService.findAllLocations());
    }
}
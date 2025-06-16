package org.prd.resourceserver.util.mapper;

import org.prd.resourceserver.persistence.dto.LocationPageDto;
import org.prd.resourceserver.persistence.entity.Location;

public class LocationMapper {

    public static LocationPageDto toPageDto(Location location) {
        return new LocationPageDto(
                location.getId(),
                location.getName(),
                location.getAddress(),
                location.getCreatedAt(),
                location.getUpdatedAt()
        );
    }
}
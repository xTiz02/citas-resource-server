package org.prd.resourceserver.persistence.dto;

import java.util.Date;
import java.util.List;
import org.prd.resourceserver.persistence.entity.Location;

public record SpecialtyPageDto(
    Long id,
    String name,
    Date creationDate,
    Date updateDate,
    List<LocationPageDto> locations,
    boolean enabled
) {
}
package org.prd.resourceserver.persistence.dto;

import java.util.Date;

public record SpecialtyPageDto(
    Long id,
    String name,
    Date creationDate,
    Date updateDate,
    boolean enabled
) {
}
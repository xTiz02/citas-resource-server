package org.prd.resourceserver.persistence.dto;

import java.util.Date;

public record LocationPageDto(
    Long id,
    String name,
    String address,
    Date creationDate,
    Date updateDate
) {
}
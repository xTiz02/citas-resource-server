package org.prd.resourceserver.persistence.dto;

import java.util.Date;

public record ApiResponse(
        String message,
        Date timestamp,
        boolean success
) {
}
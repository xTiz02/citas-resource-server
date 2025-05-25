package org.prd.resourceserver.persistence.dto;

public record ApiResponse(
        String message,
        String timestamp,
        boolean success
) {
}
package org.prd.resourceserver.persistence.patient.dto;

public record ApiResponse<T>(
    String message,
    String error,
    T data,
    boolean success
) {

}
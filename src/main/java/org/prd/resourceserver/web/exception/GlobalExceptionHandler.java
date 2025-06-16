package org.prd.resourceserver.web.exception;

import org.prd.resourceserver.persistence.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //Controla los errores no esperados
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handlerException(Exception e,
                                                        WebRequest webRequest) {
        log.error(String.format("Error no esperado %s: %s", webRequest.getDescription(false), e.getMessage()));
        e.printStackTrace();
        return new ResponseEntity<>(new ApiResponse("Error inesperado: " + e.getMessage(), new Date(), false), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
package org.prd.resourceserver.persistence.dto;

import java.time.LocalDate;
import org.prd.resourceserver.util.TurnEnum;

public record CreateExceptionDto(
    Long exceptionId,
    LocalDate dateException,
    TurnEnum turn
) {

}
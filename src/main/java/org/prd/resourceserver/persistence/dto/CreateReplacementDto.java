package org.prd.resourceserver.persistence.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDate;
import java.util.Date;
import org.prd.resourceserver.persistence.entity.Doctor;
import org.prd.resourceserver.util.TurnEnum;

public record CreateReplacementDto(
    Long replacementId,
    Long absentDoctorId,
    Long coveringDoctor,
    LocalDate dateFrom,
    LocalDate dateTo,
    TurnEnum turn
) {

}
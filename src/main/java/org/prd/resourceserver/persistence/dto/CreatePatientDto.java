package org.prd.resourceserver.persistence.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.prd.resourceserver.persistence.entity.Appointment;
import org.prd.resourceserver.persistence.entity.User;
import org.prd.resourceserver.util.GenderEnum;

public record CreatePatientDto(
    Long id,
    String name,
    String lastName,
    String dni,
    GenderEnum gender,
    String documentType,
    LocalDate birthDate,
    String email,
    String phone,
    String address,
    Long userId
) {

}

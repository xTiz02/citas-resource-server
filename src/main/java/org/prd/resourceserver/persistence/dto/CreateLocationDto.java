package org.prd.resourceserver.persistence.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.prd.resourceserver.persistence.entity.Specialty;

public record CreateLocationDto(
    Long id,
    String name,
    String address
) {

}

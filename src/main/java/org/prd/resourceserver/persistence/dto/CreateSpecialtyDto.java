package org.prd.resourceserver.persistence.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.prd.resourceserver.persistence.entity.Doctor;
import org.prd.resourceserver.persistence.entity.Location;

public record CreateSpecialtyDto(
    Long id,
    String name,
    List<Integer> locationIds
) {

}

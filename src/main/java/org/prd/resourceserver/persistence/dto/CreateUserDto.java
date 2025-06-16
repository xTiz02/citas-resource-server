package org.prd.resourceserver.persistence.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.prd.resourceserver.persistence.entity.Role;
import org.prd.resourceserver.util.RoleEnum;

public record CreateUserDto(
    Long id,
    String username,
    String password,
    RoleEnum role,
    boolean accountBlocked,
    boolean enabled
) {

}

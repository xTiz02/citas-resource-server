package org.prd.resourceserver.persistence.dto;


import org.prd.resourceserver.util.RoleEnum;

import java.util.Date;

public record UserDetailsDto(
        String username,
        boolean account_locked,
        boolean enabled,
        RoleEnum role,
        Date createdAt,
        Date updatedAt,
        String password

) {
}
package org.prd.resourceserver.persistence.dto;

import org.prd.resourceserver.util.RoleEnum;

import java.util.Date;

public record UserPageDto(
    String username,
    boolean account_locked,
    boolean enabled,
    RoleEnum role,
    Date createdAt,
    Date updatedAt) {
}
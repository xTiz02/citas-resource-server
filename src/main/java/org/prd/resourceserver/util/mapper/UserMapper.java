package org.prd.resourceserver.util.mapper;


import org.prd.resourceserver.persistence.dto.UserDetailsDto;
import org.prd.resourceserver.persistence.dto.UserPageDto;
import org.prd.resourceserver.persistence.entity.User;

public class UserMapper {

    public static UserDetailsDto toUserDetailsDto(User user){
        return new UserDetailsDto(
                user.getUsername(),
                user.isAccount_locked(),
                user.isEnabled(),
                user.getRole().getName(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getPassword()
        );
    }

    public static UserPageDto toPageDto(User user) {
        return new UserPageDto(
                user.getUsername(),
                user.isAccount_locked(),
                user.isEnabled(),
                user.getRole().getName(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
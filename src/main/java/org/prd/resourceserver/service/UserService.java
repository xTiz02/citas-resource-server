package org.prd.resourceserver.service;

import org.prd.resourceserver.persistence.dto.PageResponse;
import org.prd.resourceserver.persistence.dto.UserDetailsDto;
import org.prd.resourceserver.persistence.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    PageResponse<UserDto> getAllUsers(Pageable pageable);
    UserDetailsDto getUserByUsername(String username);
    void updateUser(String username, UserDetailsDto userDetailsDto);
    void deleteUserByiD(Long id);
    void lockUserAccount(String username);
    void unlockUserAccount(String username);
}
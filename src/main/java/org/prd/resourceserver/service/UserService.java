package org.prd.resourceserver.service;

import org.prd.resourceserver.persistence.dto.CreateUserDto;
import org.prd.resourceserver.persistence.dto.PageResponse;
import org.prd.resourceserver.persistence.dto.UserDetailsDto;
import org.prd.resourceserver.persistence.dto.UserPageDto;
import org.springframework.data.domain.Pageable;

public interface UserService {
    PageResponse<UserPageDto> getAllUsers(Pageable pageable);
    UserDetailsDto getUserByUsername(String username);
    UserPageDto createUser(CreateUserDto createUserDto);
    UserPageDto updateUser(CreateUserDto userDetailsDto);
    UserPageDto getUserById(Long id);
    void deleteUserById(Long id);
    void lockUserAccount(String username);
    void unlockUserAccount(String username);
}
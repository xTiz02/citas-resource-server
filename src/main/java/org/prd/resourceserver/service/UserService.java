package org.prd.resourceserver.service;

import org.prd.resourceserver.persistence.dto.PageResponse;
import org.prd.resourceserver.persistence.dto.UserDetailsDto;
import org.prd.resourceserver.persistence.dto.UserPageDto;
import org.springframework.data.domain.Pageable;

public interface UserService {
    PageResponse<UserPageDto> getAllUsers(Pageable pageable);
    UserDetailsDto getUserByUsername(String username);
//    void updateUser(UserDto userDto);
//    void createUser(UserDto userDto);
//    void deleteUserByiD(Long id);
//    void lockUserAccount(String username);
//    void unlockUserAccount(String username);
}
package org.prd.resourceserver.service.impl;

import org.prd.resourceserver.persistence.dto.CreateUserDto;
import org.prd.resourceserver.persistence.dto.PageResponse;
import org.prd.resourceserver.persistence.dto.UserDetailsDto;
import org.prd.resourceserver.persistence.dto.UserPageDto;
import org.prd.resourceserver.persistence.entity.User;
import org.prd.resourceserver.persistence.repository.UserRepository;
import org.prd.resourceserver.service.UserService;
import org.prd.resourceserver.util.mapper.UserMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public PageResponse<UserPageDto> getAllUsers(Pageable pageable) {
        var page = userRepository.findAllUsers(pageable);
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast(),
                page.isFirst()
        );
    }

    @Override
    public UserDetailsDto getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return UserMapper.toUserDetailsDto(user.get());
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public UserPageDto createUser(CreateUserDto createUserDto) {
        return null;
    }

    @Override
    public UserPageDto updateUser(UserDetailsDto userDetailsDto) {
        return null;
    }

    @Override
    public UserPageDto getUserById(Long id) {
        return null;
    }

    @Override
    public void deleteUserById(Long id) {

    }

    @Override
    public void lockUserAccount(String username) {

    }

    @Override
    public void unlockUserAccount(String username) {

    }

}
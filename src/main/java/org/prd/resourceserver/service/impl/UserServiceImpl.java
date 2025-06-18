package org.prd.resourceserver.service.impl;

import org.prd.resourceserver.persistence.dto.CreateUserDto;
import org.prd.resourceserver.persistence.dto.PageResponse;
import org.prd.resourceserver.persistence.dto.UserDetailsDto;
import org.prd.resourceserver.persistence.dto.UserPageDto;
import org.prd.resourceserver.persistence.entity.User;
import org.prd.resourceserver.persistence.repository.RoleRepository;
import org.prd.resourceserver.persistence.repository.UserRepository;
import org.prd.resourceserver.service.UserService;
import org.prd.resourceserver.util.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
      this.roleRepository = roleRepository;
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
        User user = new User();
        user.setUsername(createUserDto.username());
        user.setRole(roleRepository.findByName(createUserDto.role()));
        user.setPassword(bCryptPasswordEncoder.encode(createUserDto.password()));
        return UserMapper.toPageDto(userRepository.save(user));
    }

    @Override
    public UserPageDto updateUser(CreateUserDto userDetailsDto) {
        User userOptional = userRepository.findById(userDetailsDto.id())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(userDetailsDto.password() != null){
            userOptional.setPassword(bCryptPasswordEncoder.encode(userDetailsDto.password()));
        }
        userOptional.setUsername(userDetailsDto.username());
        userOptional.setRole(roleRepository.findByName(userDetailsDto.role()));
        userOptional = userRepository.save(userOptional);
        return UserMapper.toPageDto(userOptional);
    }

    @Override
    public UserPageDto getUserById(Long id) {
        User userOptional = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toPageDto(userOptional);
    }

    @Override
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    public void lockUserAccount(String username) {

    }

    @Override
    public void unlockUserAccount(String username) {

    }

}
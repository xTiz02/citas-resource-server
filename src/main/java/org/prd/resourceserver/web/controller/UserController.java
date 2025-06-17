package org.prd.resourceserver.web.controller;

import org.prd.resourceserver.persistence.dto.CreateUserDto;
import org.prd.resourceserver.persistence.dto.PageResponse;
import org.prd.resourceserver.persistence.dto.UserDetailsDto;
import org.prd.resourceserver.persistence.dto.UserPageDto;
import org.prd.resourceserver.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<PageResponse<UserPageDto>> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<UserPageDto> createUser(@RequestBody CreateUserDto userPageDto) {
        return ResponseEntity.ok(userService.createUser(userPageDto));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDetailsDto> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @PutMapping("/update")
    public ResponseEntity<UserPageDto> updateUser(@RequestBody UserDetailsDto userDetailsDto) {
        return ResponseEntity.ok(userService.updateUser(userDetailsDto));
    }


}
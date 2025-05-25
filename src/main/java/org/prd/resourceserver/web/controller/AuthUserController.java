package org.prd.resourceserver.web.controller;

import org.prd.resourceserver.persistence.dto.PageResponse;
import org.prd.resourceserver.persistence.dto.UserDetailsDto;
import org.prd.resourceserver.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@CrossOrigin(origins = "http://localhost:8085")
public class AuthUserController {

    private final UserService userService;

    public AuthUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/find/{username}")
    public ResponseEntity<UserDetailsDto> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }
}
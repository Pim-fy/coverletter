package com.coverletter.coverletter.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import com.coverletter.coverletter.dto.AuthDto;
import com.coverletter.coverletter.service.UserService;

@RestController
@RequestMapping("/Auth")
public class AuthController {
    
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping
    public AuthDto.AuthResponse authRequest(@RequestBody @Valid AuthDto.AuthRequest request) {
        return userService.authResponse(request);
    }
}

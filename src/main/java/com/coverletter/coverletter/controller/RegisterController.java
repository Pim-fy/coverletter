package com.coverletter.coverletter.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coverletter.coverletter.dto.RegisterDto;
import com.coverletter.coverletter.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/Register")
public class RegisterController {

    private final UserService userService;
    
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public RegisterDto.RegisterResponse registerRequest(@RequestBody @Valid RegisterDto.RegisterRequest request) {
        return userService.registerResponse(request);
    }
}

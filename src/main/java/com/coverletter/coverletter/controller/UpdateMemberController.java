package com.coverletter.coverletter.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coverletter.coverletter.dto.UpdateMemberDto;
import com.coverletter.coverletter.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/UpdateMember")
public class UpdateMemberController {

    private final UserService userService;

    public UpdateMemberController(UserService userService) {
        this.userService = userService;
    }
    
    @PutMapping("/{userId}")
    public String updateMember(
        @PathVariable Long userId,
        @RequestBody @Valid UpdateMemberDto updateMemberDto) {
            boolean updated = userService.updateMember(userId, updateMemberDto);
            if(updated) {
                return "회원 정보 수정 완료.";
            } else {
                return "회원 정보를 찾을 수 없습니다.";
            }
    }
}

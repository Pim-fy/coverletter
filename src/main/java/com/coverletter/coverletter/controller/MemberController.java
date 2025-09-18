package com.coverletter.coverletter.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coverletter.coverletter.dto.ReadMemberDto;
import com.coverletter.coverletter.dto.UpdateMemberDto;
import com.coverletter.coverletter.service.MemberService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/Member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/{userId}")        // 저장된 회원 정보 조회
    public ReadMemberDto.ReadMemberResponse readMember(@PathVariable Long userId) {
        return memberService.readMemberResponse(userId);
    } 


    @PutMapping("/{userId}")        // 회원 정보 수정
    public UpdateMemberDto.UpdateMemberResponse updateMemberRequest(@PathVariable Long userId, @RequestBody @Valid UpdateMemberDto.UpdateMemberRequest request) {
        return memberService.updateMemberResponse(userId, request);
    }


    
}

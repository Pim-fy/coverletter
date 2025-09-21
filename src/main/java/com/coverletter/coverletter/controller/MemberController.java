package com.coverletter.coverletter.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.coverletter.coverletter.dto.MemberDto;
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
    public MemberDto.ReadResponse readResponse(@PathVariable("userId") Long userId) {
        return memberService.readResponse(userId);
    } 

    @PutMapping("/{userId}")        // 회원 정보 수정
    public MemberDto.UpdateResponse updateResponse(@PathVariable("userId") Long userId, @RequestBody @Valid MemberDto.UpdateRequest request) {
        return memberService.updateResponse(userId, request);
    }

    // [추가] 프로필 이미지 업로드 API
    @PostMapping("/{userId}/profileImage")
    public ResponseEntity<?> uploadProfileImage(
        @PathVariable("userId") Long userId,
        @RequestParam("profileImage") MultipartFile file) {
        
        try {
            memberService.updateProfileImage(userId, file);
            return ResponseEntity.ok().body(Map.of("success", true, "message", "이미지 업로드 성공"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}

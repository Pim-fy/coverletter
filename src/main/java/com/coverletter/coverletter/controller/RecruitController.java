package com.coverletter.coverletter.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.coverletter.coverletter.dto.RecruitDto;
import com.coverletter.coverletter.service.RecruitService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/Recruit")
public class RecruitController {
    private final RecruitService recruitService;

    public RecruitController(RecruitService recruitService) {
        this.recruitService = recruitService;
    }

    @PostMapping("/{userId}")       // 지원 정보 생성
    public RecruitDto.CreateResponse createResponse(@PathVariable("userId") Long userId, @RequestBody @Valid RecruitDto.CreateRequest request) {
        return recruitService.createResponse(userId, request);
    }

    @GetMapping("/{userId}")        // 지원 정보 조회
    public RecruitDto.ReadResponse readResponse(@PathVariable("userId") Long userId) {
        return recruitService.readResponse(userId);
    }
    
    @PutMapping("/{userId}")        // 지원 정보 수정
    public RecruitDto.UpdateResponse updateResponse(@PathVariable("userId") Long userId, @RequestBody @Valid RecruitDto.UpdateRequest request) {
        return recruitService.updateResponse(userId, request);
    }
    @DeleteMapping("/{userId}/{recruitId}")      // 지원 정보 삭제
    public RecruitDto.DeleteResponse deleteResponse(@PathVariable("userId") Long userId, @PathVariable("recruitId") Long recruitId) {
        return recruitService.deleteResponse(userId, recruitId);
    }

    // 모든 지원 정보 삭제 컨트롤러
    @DeleteMapping("/user/{userId}")
    public RecruitDto.DeleteResponse deleteAllResponse(@PathVariable("userId") Long userId) {
        return recruitService.deleteAllResponse(userId);
    }
}

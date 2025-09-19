package com.coverletter.coverletter.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coverletter.coverletter.dto.EducationDto;
import com.coverletter.coverletter.service.EducationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/Education")
public class EducationController {
    private final EducationService educationService;

    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    @PostMapping("/{userId}")         // 학력 정보 생성
    public EducationDto.CreateResponse createResponse(@PathVariable Long userId, @RequestBody @Valid EducationDto.CreateRequest request) {
        return educationService.createResponse(userId, request);
    }

    @GetMapping("/{userId}")          // 학력 정보 조회
    public EducationDto.ReadResponse readResponse(@PathVariable Long userId) {
        return educationService.readResponse(userId);
    }
    @PutMapping("/{userId}")          // 학력 정보 수정
    public EducationDto.UpdateResponse updateResponse(@PathVariable Long userId, @RequestBody @Valid EducationDto.UpdateRequest request) {
        return educationService.updateResponse(userId, request);
    }
    @DeleteMapping("/{userId}/{educationId}")      // 학력 정보 삭제
    public EducationDto.DeleteResponse deleteResponse(@PathVariable Long userId, @PathVariable Long educationId) {
        return educationService.deleteResponse(userId, educationId);
    }
}

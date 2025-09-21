package com.coverletter.coverletter.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coverletter.coverletter.dto.QualificationDto;
import com.coverletter.coverletter.service.QualificationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/Qualification")
public class QualificationController {
    private final QualificationService qualificationService;

    public QualificationController(QualificationService qualificationService) {
        this.qualificationService = qualificationService;
    }

    @PostMapping("/{userId}")           // 자격 정보 생성
    public QualificationDto.CreateResponse createResponse(@PathVariable("userId")  Long userId, @RequestBody @Valid QualificationDto.CreateRequest request) {
        return qualificationService.createResponse(userId, request);
    }

    @GetMapping("/{userId}")            // 자격 정보 조회
    public QualificationDto.ReadResponse readResponse(@PathVariable("userId")  Long userId) {
        return qualificationService.readResponse(userId);
    }

    @PutMapping("/{userId}")            // 자격 정보 수정
    public QualificationDto.UpdateResponse updateResponse(@PathVariable("userId")  Long userId, @RequestBody @Valid QualificationDto.UpdateRequest request) {
        return qualificationService.updateResponse(userId, request);
    }

    @DeleteMapping("/{userId}/{qualificationId}") // 자격 정보 삭제
    public QualificationDto.DeleteResponse deleteResponse(@PathVariable("userId")  Long userId, @PathVariable("qualificationId")  Long qualificationId) {
        return qualificationService.deleteResponse(userId, qualificationId);
    }
}

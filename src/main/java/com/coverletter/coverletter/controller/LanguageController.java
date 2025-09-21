package com.coverletter.coverletter.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.coverletter.coverletter.dto.LanguageDto;
import com.coverletter.coverletter.service.LanguageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/Language")
public class LanguageController {
    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @PostMapping("/{userId}")       // 언어 정보 생성
    public LanguageDto.CreateResponse createResponse(@PathVariable("userId") Long userId, @RequestBody @Valid LanguageDto.CreateRequest request) {
        return languageService.createResponse(userId, request);
    }

    @GetMapping("/{userId}")        // 언어 정보 조회
    public LanguageDto.ReadResponse readResponse(@PathVariable("userId") Long userId) {
        return languageService.readResponse(userId);
    }
    
    @PutMapping("/{userId}")        // 언어 정보 수정
    public LanguageDto.UpdateResponse updateResponse(@PathVariable("userId") Long userId, @RequestBody @Valid LanguageDto.UpdateRequest request) {
        return languageService.updateResponse(userId, request);
    }
    @DeleteMapping("/{userId}/{careerId}")      // 언어 정보 삭제
    public LanguageDto.DeleteResponse deleteResponse(@PathVariable("userId") Long userId, @PathVariable("languageId") Long languageId) {
        return languageService.deleteResponse(userId, languageId);
    }
}

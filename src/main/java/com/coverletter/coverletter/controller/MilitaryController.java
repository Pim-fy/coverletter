package com.coverletter.coverletter.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coverletter.coverletter.dto.MilitaryDto;
import com.coverletter.coverletter.service.MilitaryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/Military")
public class MilitaryController {
    private final MilitaryService militaryService;

    public MilitaryController(MilitaryService militaryService) {
        this.militaryService = militaryService;
    }

    @PostMapping("/{userId}")       // 병역 정보 생성
    public MilitaryDto.CreateResponse createResponse(@PathVariable Long userId, @RequestBody @Valid MilitaryDto.CreateRequest request) {
        return militaryService.createResponse(userId, request);
    }

    @GetMapping("/{userId}")        // 병역 정보 조회
    public MilitaryDto.ReadResponse readResponse(@PathVariable Long userId) {
        return militaryService.readResponse(userId);
    } 
    @PutMapping("/{userId}")        // 병역 정보 수정
    public MilitaryDto.UpdateResponse updateResponse(@PathVariable Long userId, @RequestBody @Valid MilitaryDto.UpdateRequest request) {
        return militaryService.updateResponse(userId, request);
    }
    @DeleteMapping("/{userId}/{militaryId}")        // 병역 정보 삭제
    public MilitaryDto.DeleteResponse deleteResponse(@PathVariable Long userId, @PathVariable Long militaryId) {
        return militaryService.deleteResponse(userId, militaryId);
    }
}

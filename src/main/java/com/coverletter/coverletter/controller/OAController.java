package com.coverletter.coverletter.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coverletter.coverletter.dto.OADto;
import com.coverletter.coverletter.service.OAService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/OA")
public class OAController {
    private final OAService oaService;

    public OAController(OAService oaService) {
        this.oaService = oaService;
    }
    
    @PostMapping("/{userId}")           // 컴퓨터능력 정보 생성
    public OADto.CreateResponse createResponse(@PathVariable Long userId, @RequestBody @Valid OADto.CreateRequest request) {
        return oaService.createResponse(userId, request);
    }
    
    @GetMapping("/{userId}")            // 컴퓨터능력 정보 조회
    public OADto.ReadResponse readResponse(@PathVariable Long userId) {
        return oaService.readResponse(userId);
    }

    @PutMapping("/{userId}")            // 컴퓨터능력 정보 수정
    public OADto.UpdateResponse updateResponse(@PathVariable Long userId, @RequestBody @Valid OADto.UpdateRequest request) {
        return oaService.updateResponse(userId, request);
    }
    @DeleteMapping("/{userId}/{oaId}")       // 컴퓨터능력 정보 삭제
    public OADto.DeleteResponse deleteResponse(@PathVariable Long userId, @PathVariable Long oaId) {
        return oaService.deleteResponse(userId, oaId);
    }
}

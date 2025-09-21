package com.coverletter.coverletter.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.coverletter.coverletter.dto.PrizeDto;
import com.coverletter.coverletter.service.PrizeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/Prize")
public class PrizeController {
    private final PrizeService prizeService;

    public PrizeController(PrizeService prizeService) {
        this.prizeService = prizeService;
    }

    @PostMapping("/{userId}")       // 수상 정보 생성
    public PrizeDto.CreateResponse createResponse(@PathVariable("userId") Long userId, @RequestBody @Valid PrizeDto.CreateRequest request) {
        return prizeService.createResponse(userId, request);
    }

    @GetMapping("/{userId}")        // 수상 정보 조회
    public PrizeDto.ReadResponse readResponse(@PathVariable("userId") Long userId) {
        return prizeService.readResponse(userId);
    }
    
    @PutMapping("/{userId}")        // 수상 정보 수정
    public PrizeDto.UpdateResponse updateResponse(@PathVariable("userId") Long userId, @RequestBody @Valid PrizeDto.UpdateRequest request) {
        return prizeService.updateResponse(userId, request);
    }
    @DeleteMapping("/{userId}/{prizeId}")      // 수상 정보 삭제
    public PrizeDto.DeleteResponse deleteResponse(@PathVariable("userId") Long userId, @PathVariable("prizeId") Long prizeId) {
        return prizeService.deleteResponse(userId, prizeId);
    }
}

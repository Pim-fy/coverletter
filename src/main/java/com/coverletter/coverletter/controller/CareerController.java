package com.coverletter.coverletter.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.coverletter.coverletter.dto.CareerDto;
import com.coverletter.coverletter.service.CareerService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/Career")
public class CareerController {
    private final CareerService careerService;

    public CareerController(CareerService careerService) {
        this.careerService = careerService;
    }

    @PostMapping("/{userId}")       // 경력 정보 생성
    public CareerDto.CreateResponse createResponse(@PathVariable Long userId, @RequestBody @Valid CareerDto.CreateRequest request) {
        return careerService.createResponse(userId, request);
    }

    @GetMapping("/{userId}")        // 저장된 경력 정보 조회
    public CareerDto.ReadResponse readResponse(@PathVariable Long userId) {
        return careerService.readResponse(userId);
    }
    
    @PutMapping("/{userId}")        // 경력 정보 수정
    public CareerDto.UpdateResponse updateResponse(@PathVariable Long userId, @RequestBody @Valid CareerDto.UpdateRequest request) {
        return careerService.updateResponse(userId, request);
    }
    @DeleteMapping("/{userId}/{careerId}")      // 경력 정보 삭제
    public CareerDto.DeleteResponse deleteResponse(@PathVariable Long userId, @PathVariable Long careerId) {
        return careerService.deleteResponse(userId, careerId);
    }

}

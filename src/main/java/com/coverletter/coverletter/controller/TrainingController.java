package com.coverletter.coverletter.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.coverletter.coverletter.service.TrainingService;
import com.coverletter.coverletter.dto.TrainingDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/Training")
public class TrainingController {
    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @PostMapping("/{userId}")       // 교육 정보 생성
    public TrainingDto.CreateResponse createResponse(@PathVariable("userId") Long userId, @RequestBody @Valid TrainingDto.CreateRequest request) {
        return trainingService.createResponse(userId, request);
    }

    @GetMapping("/{userId}")        // 교육 정보 조회
    public TrainingDto.ReadResponse readResponse(@PathVariable("userId") Long userId) {
        return trainingService.readResponse(userId);
    }
    
    @PutMapping("/{userId}")        // 교육 정보 수정
    public TrainingDto.UpdateResponse updateResponse(@PathVariable("userId") Long userId, @RequestBody @Valid TrainingDto.UpdateRequest request) {
        return trainingService.updateResponse(userId, request);
    }
    @DeleteMapping("/{userId}/{trainingId}")      // 교육 정보 삭제
    public TrainingDto.DeleteResponse deleteResponse(@PathVariable("userId") Long userId, @PathVariable("trainingId") Long trainingId) {
        return trainingService.deleteResponse(userId, trainingId);
    }
}

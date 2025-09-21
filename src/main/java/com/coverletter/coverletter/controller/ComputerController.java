package com.coverletter.coverletter.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coverletter.coverletter.dto.ComputerDto;
import com.coverletter.coverletter.service.ComputerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/Computer")
public class ComputerController {
    private final ComputerService computerService;

    public ComputerController(ComputerService computerService) {
        this.computerService = computerService;
    }
    
    @PostMapping("/{userId}")       // 컴퓨터 능력 정보 생성
    public ComputerDto.CreateResponse createResponse(@PathVariable("userId") Long userId, @RequestBody @Valid ComputerDto.CreateRequest request) {
        return computerService.createResponse(userId, request);
    }

    @GetMapping("/{userId}")        // 컴퓨터 능력 정보 조회
    public ComputerDto.ReadResponse readResponse(@PathVariable("userId") Long userId) {
        return computerService.readResponse(userId);
    }

    @PutMapping("/{userId}")        // 컴퓨터 능력 정보 수정
    public ComputerDto.UpdateResponse updateResponse(@PathVariable("userId") Long userId, @RequestBody @Valid ComputerDto.UpdateRequest request) {
        return computerService.updateResponse(userId, request);
    }

    @DeleteMapping("/{userId}/{computerId}")      // 컴퓨터 능력 정보 삭제
    public ComputerDto.DeleteResponse deleteResponse(@PathVariable("userId") Long userId, @PathVariable("computerId") Long computerId) {
        return computerService.deleteResponse(userId, computerId);
    }

    @DeleteMapping("/user/{userId}")        // 컴퓨터 능력 정보 전체 삭제
    public ComputerDto.DeleteResponse deleteAllResponse(@PathVariable("userId") Long userId) {
        return computerService.deleteAllResponse(userId);
    }
}

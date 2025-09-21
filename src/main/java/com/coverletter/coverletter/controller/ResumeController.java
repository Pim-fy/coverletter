package com.coverletter.coverletter.controller;

import com.coverletter.coverletter.dto.ResumeDto;
import com.coverletter.coverletter.entity.Member;
import com.coverletter.coverletter.repository.MemberRepository;
import com.coverletter.coverletter.service.ResumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResumeController {

    private final ResumeService resumeService;
    private final MemberRepository memberRepository; // MemberRepository 주입

    public ResumeController(ResumeService resumeService, MemberRepository memberRepository) {
        this.resumeService = resumeService;
        this.memberRepository = memberRepository; // 주입
    }

    @GetMapping("/api/resume")
    public ResponseEntity<ResumeDto> getResumeData() {
        // SecurityContext에서 현재 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = authentication.getName(); // 현재 로그인된 사용자의 loginId

        // loginId를 사용하여 Member 엔티티(userId 포함)를 조회
        Member member = memberRepository.findByLoginId(loginId)
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        
        Long userId = member.getUserId();

        ResumeDto fullResume = resumeService.getFullResume(userId);
        return ResponseEntity.ok(fullResume);
    }
}
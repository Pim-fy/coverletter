package com.coverletter.coverletter.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.coverletter.coverletter.dto.MemberDto;
import com.coverletter.coverletter.entity.Member;
import com.coverletter.coverletter.repository.MemberRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class MemberService {
    
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원 정보 조회 로직(이력서)
    public MemberDto.ReadResponse readResponse(Long userId) {
        Optional<Member> memberOpt = memberRepository.findByUserId(userId);
        
        if (memberOpt.isPresent()) {
            Member member = memberOpt.get();
            // [수정] 생성자를 사용하고, userId를 포함하여 모든 필드를 설정합니다.
            return new MemberDto.ReadResponse(
                true,
                "조회 성공",
                member.getUserId(), // userId 추가
                member.getName(),
                member.getPhoneNumber(),
                member.getEmergencyPhoneNumber(),
                member.getAddress(),
                member.getEmail(),
                member.getDateOfBirth(),
                member.getProfileImagePath()
            );
        } else {
            // 조회 실패 시 success: false 와 메시지만 있는 응답 반환
            return new MemberDto.ReadResponse(false, "조회 실패", null, null, null, null, null, null, null, null);
        }
    }
    
    // 회원정보 수정(이력서)
    public MemberDto.UpdateResponse updateResponse(Long userId, MemberDto.UpdateRequest dto) {
        Optional<Member> memberOpt = memberRepository.findByUserId(userId);
        if(memberOpt.isPresent()) {
            Member member = memberOpt.get();
            if(dto.getPassword() != null && !dto.getPassword().isEmpty()) {member.updateMember(dto, passwordEncoder);}
            if(dto.getName() != null) {member.setName(dto.getName());} 
            if(dto.getPhoneNumber() != null) {member.setPhoneNumber(dto.getPhoneNumber());}
            if(dto.getEmergencyPhoneNumber() != null) {member.setEmergencyPhoneNumber(dto.getEmergencyPhoneNumber());}
            if(dto.getAddress() != null) {member.setAddress(dto.getAddress());}
            if(dto.getEmail() != null) {member.setEmail(dto.getEmail());}
            if(dto.getDateOfBirth() != null) {member.setDateOfBirth(dto.getDateOfBirth());}
            if(dto.getProfileImagePath() != null) {member.setProfileImagePath(dto.getProfileImagePath());}
            memberRepository.save(member);
            return new MemberDto.UpdateResponse(true, "수정 완료.");
        } else {
            return new MemberDto.UpdateResponse(false, "수정 실패.");
        }
    }


    // [추가] application.properties에서 파일 저장 경로 가져오기
    @Value("${file.upload-dir}")
    private String uploadDir;

    // [추가] 프로필 이미지 업데이트 로직
    public void updateProfileImage(Long userId, MultipartFile file) throws Exception {
        Member member = memberRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (file.isEmpty()) {
            throw new Exception("업로드할 파일을 선택해주세요.");
        }

        // 고유한 파일 이름 생성
        String originalFileName = file.getOriginalFilename();
        String storedFileName = UUID.randomUUID().toString() + "_" + originalFileName;
        
        // 파일 저장 경로 설정
        Path destinationPath = Paths.get(uploadDir + File.separator + storedFileName);

        // 파일 저장
        Files.copy(file.getInputStream(), destinationPath);

        // DB에 파일 경로 저장
        member.setProfileImagePath("/uploads/" + storedFileName); // 웹에서 접근할 경로
        memberRepository.save(member);
    }


    
    
    
}



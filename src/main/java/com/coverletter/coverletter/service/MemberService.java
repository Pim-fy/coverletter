package com.coverletter.coverletter.service;

import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coverletter.coverletter.dto.ReadMemberDto;
import com.coverletter.coverletter.dto.UpdateMemberDto;
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
    public ReadMemberDto.ReadMemberResponse readMemberResponse(Long userId) {
        Optional<Member> memberOpt = memberRepository.findByUserId(userId);
        if(memberOpt.isPresent()) {
            Member member = memberOpt.get();
            
            ReadMemberDto.ReadMemberResponse response = new ReadMemberDto.ReadMemberResponse();
            response.setSuccess(true);
            response.setMessage("조회 성공");
            response.setName(member.getName());
            response.setPhoneNumber(member.getPhoneNumber());
            response.setEmergencyPhoneNumber(member.getEmergencyPhoneNumber());
            response.setAddress(member.getAddress());
            response.setEmail(member.getEmail());
            response.setDateOfBirth(member.getDateOfBirth());
            response.setProfileImagePath(member.getProfileImagePath());

            return response;
        } else {
            ReadMemberDto.ReadMemberResponse response = new ReadMemberDto.ReadMemberResponse();
            response.setSuccess(false);
            response.setMessage("조회 실패");
            
            return response;
        }
    }
    
    // 회원정보 수정(이력서)
    public UpdateMemberDto.UpdateMemberResponse updateMemberResponse(Long userId, UpdateMemberDto.UpdateMemberRequest dto) {
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
            return new UpdateMemberDto.UpdateMemberResponse(true, "수정 완료.");
        } else {
            return new UpdateMemberDto.UpdateMemberResponse(false, "수정 실패.");
        }
    }


    
    
    
}



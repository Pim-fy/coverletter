package com.coverletter.coverletter.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coverletter.coverletter.dto.UpdateMemberDto;
import com.coverletter.coverletter.entity.Member;
import com.coverletter.coverletter.repository.MemberRepository;

@Service
public class MemberService {
    
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원 정보에 있는 기본 항목 들로 이미 DB에 들어가 있기 때문에
    // 아이디에 해당하는 테이블 불러오기
    
    
    // 추가로 입력받은 정보를 저장하기(수정)
    public UpdateMemberDto.UpdateMemberResponse updateMemberResponse(Long userId, UpdateMemberDto.UpdateMemberRequest dto) {
        Optional<Member> memberOpt = memberRepository.findByUserId(userId);
        if(memberOpt.isPresent()) {
            Member member = memberOpt.get();
            member.updateMember(dto, passwordEncoder);
            member.setPhoneNumber(dto.getPhoneNumber());
            member.setEmergencyPhoneNumber(dto.getEmergencyPhoneNumber());
            member.setAddress(dto.getAddress());
            member.setEmail(dto.getEmail());
            member.setDateOfBirth(dto.getDateOfBirth());
            member.setProfileImagePath(dto.getProfileImagePath());
            memberRepository.save(member);
            return new UpdateMemberDto.UpdateMemberResponse(true, "수정 완료.");
        } else {
            return new UpdateMemberDto.UpdateMemberResponse(false, "수정 실패.");
        }
    }


    
    
    
}



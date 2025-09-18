package com.coverletter.coverletter.service;


import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coverletter.coverletter.dto.AuthDto;
import com.coverletter.coverletter.dto.RegisterDto;
import com.coverletter.coverletter.entity.Member;
import com.coverletter.coverletter.repository.MemberRepository;

@Service
public class UserService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 로그인, 회원가입

    // 로그인
    public AuthDto.AuthResponse authResponse(AuthDto.AuthRequest request) {
        Optional<Member> memberOpt = memberRepository.findByLoginId(request.getLoginId());
        if(memberOpt.isPresent() && passwordEncoder.matches(request.getPassword(), memberOpt.get().getPassword())) {
            return new AuthDto.AuthResponse(true, "로그인 성공");
        } else {
            return new AuthDto.AuthResponse(false, "아이디 또는 비밀번호가 일치하지 않습니다.");
        }
    }

    // 회원가입
    public RegisterDto.RegisterResponse registerResponse(RegisterDto.RegisterRequest request) {

        // loginId 중복체크
        if(memberRepository.findByLoginId(request.getLoginId()).isPresent()) {
            return new RegisterDto.RegisterResponse(false, "이미 존재하는 아이디입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 엔터티 변환, 저장
        Member member = request.toMemberEntity(encodedPassword);
        memberRepository.save(member);

        return new RegisterDto.RegisterResponse(true, "회원가입 완료");
    }    

}

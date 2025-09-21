package com.coverletter.coverletter.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coverletter.coverletter.dto.AuthDto;
import com.coverletter.coverletter.dto.RegisterDto;
import com.coverletter.coverletter.entity.Member;
import com.coverletter.coverletter.repository.MemberRepository;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 로그인 처리
    public Optional<Member> authenticate(AuthDto.AuthRequest request) {
        Optional<Member> memberOpt = memberRepository.findByLoginId(request.getLoginId());
        
        if (memberOpt.isEmpty()) {
            log.debug("로그인 시도: loginId={} → 사용자 없음", request.getLoginId());
            return Optional.empty();
        }

        Member member = memberOpt.get();
        String rawPassword = request.getPassword();
        String storedHash = member.getPassword();

        if (passwordEncoder.matches(rawPassword, storedHash)) {
            log.info("로그인 성공: loginId={}", request.getLoginId());
            return Optional.of(member); // 성공 시 Member 객체 반환
        } else {
            log.warn("로그인 실패(비밀번호 불일치): loginId={}", request.getLoginId());
            return Optional.empty(); // 실패 시 빈 Optional 반환
        }
    }

    // 회원가입 처리
    public RegisterDto.RegisterResponse registerResponse(RegisterDto.RegisterRequest request) {
        // loginId 중복 체크
        if (memberRepository.findByLoginId(request.getLoginId()).isPresent()) {
            log.warn("회원가입 실패(중복 아이디): loginId={}", request.getLoginId());
            return new RegisterDto.RegisterResponse(false, "이미 존재하는 아이디입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 엔티티 변환 및 저장
        Member member = request.toMemberEntity(encodedPassword);
        memberRepository.save(member);

        log.info("회원가입 성공: loginId={}", request.getLoginId());
        return new RegisterDto.RegisterResponse(true, "회원가입 완료");
    }
}

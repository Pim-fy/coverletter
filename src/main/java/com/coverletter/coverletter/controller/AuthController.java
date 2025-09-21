package com.coverletter.coverletter.controller;

import com.coverletter.coverletter.dto.AuthDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse; // response 추가
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    // SecurityContextRepository를 주입받습니다.
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity<AuthDto.AuthResponse> authRequest(
            @RequestBody @Valid AuthDto.AuthRequest request,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse // response 파라미터 추가
    ) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getLoginId(), request.getPassword())
            );

            // SecurityContext를 생성하고 인증 정보를 설정합니다.
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);

            // ✨ 핵심 코드: 인증 정보를 세션에 명시적으로 저장합니다.
            securityContextRepository.saveContext(context, httpServletRequest, httpServletResponse);

            return ResponseEntity.ok(new AuthDto.AuthResponse(true, "로그인 성공"));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new AuthDto.AuthResponse(false, "아이디 또는 비밀번호가 일치하지 않습니다."));
        }
    }
}
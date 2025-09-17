package com.coverletter.coverletter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class AuthDto {

    @Getter
    @Setter
    public static class AuthRequest {
        
        private String loginId;
        private String password;
    }

    @Getter
    @AllArgsConstructor         // 클래스의 모든 필드를 파라미터로 받는 생성자를 자동으로 생성
    @NoArgsConstructor          // 파라미터 없는 기본 생성자를 자동으로 생성
    public static class AuthResponse {

        private boolean success;
        private String message;
    }
    
}

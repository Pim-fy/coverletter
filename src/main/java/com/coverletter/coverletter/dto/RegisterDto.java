package com.coverletter.coverletter.dto;

import com.coverletter.coverletter.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class RegisterDto {

    @Getter
    @Setter
    public static class RegisterRequest {

        private String loginId;

        private String password;

        private String name;

        private String phoneNumber;

        public Member toMemberEntity(String encodedPassword) {
            return Member.builder()
                         .loginId(this.loginId)
                         .password(encodedPassword)
                         .name(this.name)
                         .phoneNumber(this.phoneNumber)
                         .build();
        }
    }


    @Getter
    @AllArgsConstructor         // 클래스의 모든 필드를 파라미터로 받는 생성자를 자동으로 생성
    @NoArgsConstructor         // 파라미터 없는 기본 생성자를 자동으로 생성
    public static class RegisterResponse{

        private boolean success;
        private String message;
    }
}

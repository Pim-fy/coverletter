package com.coverletter.coverletter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MemberDto {

    @Getter
    @Setter
    public static class ReadRequest {
        private Long userId;
    }

    @Getter
    @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class ReadResponse {
        private Boolean success;
        private String message;
        private Long userId;
        private String name;
        private String phoneNumber;
        private String emergencyPhoneNumber;
        private String address;
        private String email;
        private String dateOfBirth;
        private String profileImagePath;
    }

    @Getter
    @Setter
    public static class UpdateRequest {
        private String password;
        private String name;
        private String phoneNumber;
        private String emergencyPhoneNumber;
        private String address;
        private String email;
        private String dateOfBirth;
        private String profileImagePath;
    }

    @Getter
    @AllArgsConstructor @NoArgsConstructor
    public static class UpdateResponse {
        private boolean success;
        private String message;
    }
    
}

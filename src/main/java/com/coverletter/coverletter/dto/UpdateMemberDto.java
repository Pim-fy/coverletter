package com.coverletter.coverletter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UpdateMemberDto {

    @Getter
    @Setter
    public static class UpdateMemberRequest {
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
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateMemberResponse {

        private boolean success;
        private String message;
    }
    
    
}

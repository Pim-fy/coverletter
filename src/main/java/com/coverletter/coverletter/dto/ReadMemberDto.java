package com.coverletter.coverletter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ReadMemberDto {

    @Getter
    @Setter
    public static class ReadMemberRequest {
        
        private Long userId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReadMemberResponse {

        private Boolean success;
        private String message;
        private String name;
        private String phoneNumber;
        private String emergencyPhoneNumber;
        private String address;
        private String email;
        private String dateOfBirth;
        private String profileImagePath;
    }

    
}

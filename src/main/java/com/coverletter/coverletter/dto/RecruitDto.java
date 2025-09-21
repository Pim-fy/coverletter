package com.coverletter.coverletter.dto;

import java.util.List;

import com.coverletter.coverletter.entity.enums.RecruitmentType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class RecruitDto {
    @Getter
    @Setter
    public static class CreateRequest {
        private RecruitmentType recruitmentType;    // 구분 - 신입, 경력
        private String recruitmentPart;         // 모집부문 - 개발,...  
        private String salaryRequirement;       // 희망연봉 - String형태
    }

    @Getter
    @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class CreateResponse {
        private boolean success;
        private String message;
        private Long recruitId;
    }

    @Getter
    @Setter
    public static class ReadRequest {
        private Long userId;
    }
    
    @Getter
    @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class ReadResponse {
        private boolean success;
        private String message;
        private List<RecruitInfo> recruits;

        @Getter
        @Setter
        @AllArgsConstructor @NoArgsConstructor
        public static class RecruitInfo {
            private Long recruitId;
            private RecruitmentType recruitmentType;    // 구분 - 신입, 경력
            private String recruitmentPart;         // 모집부문 - 개발,...  
            private String salaryRequirement;       // 희망연봉 - String형태
        }

    }

    @Getter
    @Setter
    public static class UpdateRequest {
        private Long userId;
        private Long recruitId;
        private RecruitmentType recruitmentType;    // 구분 - 신입, 경력
        private String recruitmentPart;         // 모집부문 - 개발,...  
        private String salaryRequirement;       // 희망연봉 - String형태
    }
    
    @Getter
    @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class UpdateResponse {
        private boolean success;
        private String message;
    }

    @Getter
    @Setter
    public static class DeleteRequest {
        private Long userId;
        private Long recruitId;
    }

    @Getter
    @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class DeleteResponse {
        private boolean success;
        private String message;
    }
}

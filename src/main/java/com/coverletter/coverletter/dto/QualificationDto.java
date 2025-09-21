package com.coverletter.coverletter.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class QualificationDto {
    @Getter
    @Setter
    public static class CreateRequest {
        private String qualificationName;           // 자격명
        private String qualificationDate;           // 취득일
        private String qualificationCompany;        // 발급처
    }

    @Getter
    @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class CreateResponse {
        private boolean success;
        private String message;
        private Long qualificationId;
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
        private List<QualificationInfo> qualifications;

        @Getter
        @Setter
        @AllArgsConstructor @NoArgsConstructor
        public static class QualificationInfo {
            private Long qualificationId;
            private String qualificationName;           // 자격명
            private String qualificationDate;           // 취득일
            private String qualificationCompany;        // 발급처
        }

    }

    @Getter
    @Setter
    public static class UpdateRequest {
        private Long userId;
        private Long qualificationId;
        private String qualificationName;           // 자격명
        private String qualificationDate;           // 취득일
        private String qualificationCompany;        // 발급처
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
        private Long qualificationId;
    }

    @Getter
    @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class DeleteResponse {
        private boolean success;
        private String message;
    }
    
}

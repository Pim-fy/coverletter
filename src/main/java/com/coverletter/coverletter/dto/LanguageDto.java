package com.coverletter.coverletter.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class LanguageDto {

    @Getter
    @Setter
    public static class CreateRequest {
        private String Language;       // 외국어 시험명
        private String LanguageDate;   // 외국어 취득일자
        private String LanguageScore;  // 외국어 점수
    }

    @Getter
    @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class CreateResponse {
        private boolean success;
        private String message;
        private Long languageId;
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
        private List<LanguageInfo> languages;

        @Getter
        @Setter
        @AllArgsConstructor @NoArgsConstructor
        public static class LanguageInfo {
            private Long languageId;
            private String Language;       // 외국어 시험명
            private String LanguageDate;   // 외국어 취득일자
            private String LanguageScore;  // 외국어 점수
        }
    }

    @Getter
    @Setter
    public static class UpdateRequest {
        private Long userId;
        private Long LanguageId;
        private String Language;       // 외국어 시험명
        private String LanguageDate;   // 외국어 취득일자
        private String LanguageScore;  // 외국어 점수
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
        private Long LanguageId;
    }

    @Getter
    @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class DeleteResponse {
        private boolean success;
        private String message;
    } 
}

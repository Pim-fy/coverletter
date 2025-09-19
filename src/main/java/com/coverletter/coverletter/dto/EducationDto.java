package com.coverletter.coverletter.dto;

import java.util.List;

import com.coverletter.coverletter.entity.enums.EducationStatus;
import com.coverletter.coverletter.entity.enums.EducationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class EducationDto {

    @Getter
    @Setter
    public static class CreateRequest {
        private String educationStartDate;      // 입학일
        private String educationEndDate;        // 졸업일
        private EducationStatus educationStatus;    // 상태 
        private EducationType educationType;    // 고등, 대학교 구분
        private String educationSchoolName;     // 학교명
        private String educationMajor;          // 전공
        private String educationGrade;          // 학점
        private String educationLocation;       // 소재지
        private String absenceStartDate;        // 휴학 기간(시작일)
        private String absenceEndDate;          // 휴학 기간(종료일)
        private String absenceReason;           // 휴학 사유
    }

    @Getter
    @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class CreateResponse {
        private boolean success;
        private String message;
        private Long educationId;

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
        private List<EducationInfo> education;

        @Getter
        @Setter
        @AllArgsConstructor @NoArgsConstructor
        public static class EducationInfo {
            private String educationStartDate;      // 입학일
            private String educationEndDate;        // 졸업일
            private EducationStatus educationStatus;    // 상태 
            private EducationType educationType;    // 고등, 대학교 구분
            private String educationSchoolName;     // 학교명
            private String educationMajor;          // 전공
            private String educationGrade;          // 학점
            private String educationLocation;       // 소재지
            private String absenceStartDate;        // 휴학 기간(시작일)
            private String absenceEndDate;          // 휴학 기간(종료일)
            private String absenceReason;           // 휴학 사유
        }
    }

    @Getter
    @Setter
    public static class UpdateRequest {
        private Long userId;
        private Long educationId;
        private String educationStartDate;      // 입학일
        private String educationEndDate;        // 졸업일
        private EducationStatus educationStatus;    // 상태 
        private EducationType educationType;    // 고등, 대학교 구분
        private String educationSchoolName;     // 학교명
        private String educationMajor;          // 전공
        private String educationGrade;          // 학점
        private String educationLocation;       // 소재지
        private String absenceStartDate;        // 휴학 기간(시작일)
        private String absenceEndDate;          // 휴학 기간(종료일)
        private String absenceReason;           // 휴학 사유
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
        private Long educationId;
    }

    @Getter
    @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class DeleteResponse {
        private boolean success;
        private String message;
    }
}

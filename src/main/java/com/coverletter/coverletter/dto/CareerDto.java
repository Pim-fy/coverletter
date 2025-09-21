package com.coverletter.coverletter.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CareerDto {

    @Getter
    @Setter
    public static class CreateRequest {
        private String company;             // 회사명
        private String careerStartDate;     // 근무 기간(시작일)
        private String careerEndDate;       // 근무 기간(종료일)
        private String careerTask;          // 담당 업무
        private String reasonForLeaving;    // 퇴사 사유
    }

    @Getter
    @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class CreateResponse {
        private boolean success;
        private String message;
        private Long careerId;      // 생성 후 반환되는 고유ID
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
        private List<CareerInfo> careers;

        @Getter
        @Setter
        @AllArgsConstructor @NoArgsConstructor
        public static class CareerInfo {
            private Long careerId; 
            private String company;             // 회사명
            private String careerStartDate;     // 근무 기간(시작일)
            private String careerEndDate;       // 근무 기간(종료일)
            private String careerTask;          // 담당 업무
            private String reasonForLeaving;    // 퇴사 사유

        }
        
    }

    @Getter
    @Setter
    public static class UpdateRequest {
        private Long userId;
        private Long careerId;
        private String company;             // 회사명
        private String careerStartDate;     // 근무 기간(시작일)
        private String careerEndDate;       // 근무 기간(종료일)
        private String careerTask;          // 담당 업무
        private String reasonForLeaving;    // 퇴사 사유
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
        private Long carrerId;
    }

    @Getter
    @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class DeleteResponse {
        private boolean success;
        private String message;
    }
}

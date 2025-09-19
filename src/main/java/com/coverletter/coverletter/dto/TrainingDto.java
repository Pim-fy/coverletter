package com.coverletter.coverletter.dto;

import java.util.List;
import com.coverletter.coverletter.entity.enums.TrainingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class TrainingDto {
    
    @Getter
    @Setter
    public static class CreateRequest {
        private String trainingStartDate;       // 교육 기간(시작일)
        private String trainingEndDate;         // 교육 기간(종료일)
        private String trainingName;            // 교육 과정명
        private String trainingContent;         // 교육 내용
        private String trainingCompany;         // 교육 기관
        private TrainingStatus trainingStatus;  // 수료 여부(교육 상태)
    }

    @Getter
    @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class CreateResponse {
        private boolean success;
        private String message;
        private Long trainingId;
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
        private List<TrainingInfo> trainings;

        @Getter
        @Setter
        @AllArgsConstructor @NoArgsConstructor
        public static class TrainingInfo {
            private String trainingStartDate;       // 교육 기간(시작일)
            private String trainingEndDate;         // 교육 기간(종료일)
            private String trainingName;            // 교육 과정명
            private String trainingContent;         // 교육 내용
            private String trainingCompany;         // 교육 기관
            private TrainingStatus trainingStatus;  // 수료 여부(교육 상태)
        }
    }

    @Getter
    @Setter
    public static class UpdateRequest {
        private Long userId;
        private Long trainingId;
        private String trainingStartDate;       // 교육 기간(시작일)
        private String trainingEndDate;         // 교육 기간(종료일)
        private String trainingName;            // 교육 과정명
        private String trainingContent;         // 교육 내용
        private String trainingCompany;         // 교육 기관
        private TrainingStatus trainingStatus;  // 수료 여부(교육 상태)
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
        private Long trainingId;
    }

    @Getter
    @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class DeleteResponse {
        private boolean success;
        private String message;
    }
}

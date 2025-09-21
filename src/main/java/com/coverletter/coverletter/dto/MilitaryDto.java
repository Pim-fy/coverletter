package com.coverletter.coverletter.dto;

import java.util.List;

import com.coverletter.coverletter.entity.enums.MilitaryStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MilitaryDto {

    @Getter
    @Setter
    public static class CreateRequest {
        private MilitaryStatus militaryStatus;      // 병역구분
        private String militaryRank;        // 계급
        private String militaryBranch;      // 병과
        private String militaryDischarge;   // 제대구분 // enum 변경 예정
        private String militaryStartDate;   // 복무기간(시작일)
        private String militaryEndDate;     // 복무기간(종료일)
    }

    @Getter
    @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class CreateResponse {
        private boolean success;
        private String message;
        private Long militaryId;
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
        private List<MilitaryInfo> militaries;

        @Getter
        @Setter
        @AllArgsConstructor @NoArgsConstructor
        public static class MilitaryInfo {
            private Long militaryId;
            private MilitaryStatus militaryStatus;      // 병역구분
            private String militaryRank;        // 계급
            private String militaryBranch;      // 병과
            private String militaryDischarge;   // 제대구분 // enum 변경 예정
            private String militaryStartDate;   // 복무기간(시작일)
            private String militaryEndDate;     // 복무기간(종료일)
        }
    }

    @Getter
    @Setter
    public static class UpdateRequest {
        private Long userId;
        private Long militaryId;
        private MilitaryStatus militaryStatus;      // 병역구분
        private String militaryRank;        // 계급
        private String militaryBranch;      // 병과
        private String militaryDischarge;   // 제대구분 // enum 변경 예정
        private String militaryStartDate;   // 복무기간(시작일)
        private String militaryEndDate;     // 복무기간(종료일)
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
        private Long militaryId;
    }

    @Getter
    @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class DeleteResponse {
        private boolean success;
        private String message;
    }
    
}

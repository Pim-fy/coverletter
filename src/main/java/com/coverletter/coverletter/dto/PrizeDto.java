package com.coverletter.coverletter.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PrizeDto {

    @Getter
    @Setter
    public static class CreateRequest {
        private String PrizeName;       // 수상 내용
        private String PrizeCompany;    // 수상처
        private String PrizeYear;       // 수상 년도
        private String Etc;             // 비고
    }

    @Getter
    @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class CreateResponse {
        private boolean success;
        private String message;
        private Long prizeId;
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
        private List<PrizeInfo> prizes;

        @Getter
        @Setter
        @AllArgsConstructor @NoArgsConstructor
        public static class PrizeInfo {
            private Long prizeId;
            private String PrizeName;       // 수상 내용
            private String PrizeCompany;    // 수상처
            private String PrizeYear;       // 수상 년도
            private String Etc;             // 비고
        }
    }

    @Getter
    @Setter
    public static class UpdateRequest {
        private Long userId;
        private Long prizeId;
        private String PrizeName;       // 수상 내용
        private String PrizeCompany;    // 수상처
        private String PrizeYear;       // 수상 년도
        private String Etc;             // 비고
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
        private Long prizeId;
    }

    @Getter
    @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class DeleteResponse {
        private boolean success;
        private String message;
    }

}

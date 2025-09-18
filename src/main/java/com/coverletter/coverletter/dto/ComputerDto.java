package com.coverletter.coverletter.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ComputerDto {
    
    @Getter
    @Setter
    public static class CreateRequest {
        private String computer;
    }

    @Getter
    @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class CreateResponse {
        private boolean success;
        private String message;
        private Long computerId;
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
        private List<ComputerInfo> computers;

        @Getter
        @Setter
        @AllArgsConstructor @NoArgsConstructor
        public static class ComputerInfo {
            private String computer;
        }
    }

    @Getter
    @Setter
    public static class UpdateRequest {
        private Long userId;
        private Long computerId;
        private String computer;
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
        private Long computerId;
    }

    @Getter
    @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class DeleteResponse {
        private boolean success;
        private String message;
    }
}

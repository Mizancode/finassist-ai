package com.finassist.ai_assistant_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class RagChatResponse {

    private String answer;
    private List<Source> sources;

    @Data
    public static class Source {

        private String fileName;
        private Object documentId;

        public Source(String fileName, Object documentId) {
            this.fileName = fileName;
            this.documentId = documentId;
        }
    }
}


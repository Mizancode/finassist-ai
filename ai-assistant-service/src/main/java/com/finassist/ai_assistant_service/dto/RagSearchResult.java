package com.finassist.ai_assistant_service.dto;

import lombok.Data;
import org.jspecify.annotations.Nullable;

import java.util.List;

@Data
public class RagSearchResult {

    private String answer;
    private List<SourceDocument> sources;

    @Data
    public static class SourceDocument {

        private String fileName;
        private Object documentId;
        private String content;

        public SourceDocument(String fileName, Object documentId, @Nullable String text) {
            this.fileName = fileName;
            this.documentId = documentId;
            this.content = text;
        }
    }
}

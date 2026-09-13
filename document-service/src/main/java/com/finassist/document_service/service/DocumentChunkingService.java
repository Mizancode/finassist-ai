package com.finassist.document_service.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentChunkingService {

    private final TokenTextSplitter tokenTextSplitter;

    public DocumentChunkingService() {
        this.tokenTextSplitter = TokenTextSplitter.builder()
                .withChunkSize(500)
                .withMinChunkSizeChars(200)
                .withMinChunkLengthToEmbed(50)
                .withMaxNumChunks(1000)
                .withKeepSeparator(true)
                .build();
    }
    public List<Document> split(List<Document> documents ) {
        return tokenTextSplitter.apply(documents);
    }
}

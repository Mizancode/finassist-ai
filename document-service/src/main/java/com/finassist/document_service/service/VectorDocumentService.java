package com.finassist.document_service.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VectorDocumentService {
    private final VectorStore vectorStore;
    private final DocumentChunkingService chunkingService;
    private static final String UPLOAD_DIRECTORY = "uploads";

    public VectorDocumentService(VectorStore vectorStore, DocumentChunkingService chunkingService) {
        this.vectorStore = vectorStore;
        this.chunkingService = chunkingService;
    }

    public int processAndStore(MultipartFile file, Long documentId) throws IOException {
        Path uploadDirectory = Path.of(UPLOAD_DIRECTORY);
        Files.createDirectories(uploadDirectory);
        String safeFileName = Path.of(file.getOriginalFilename()).getFileName().toString();
        Path filePath = uploadDirectory.resolve(safeFileName);
        Files.write(filePath, file.getBytes());
        FileSystemResource resource = new FileSystemResource(filePath);
        PagePdfDocumentReader reader = new PagePdfDocumentReader(resource, PdfDocumentReaderConfig.builder().withPagesPerDocument(1).build());
        List<Document> pages = reader.read();
        List<Document> chunks = chunkingService.split(pages);
        for (Document chunk : chunks) {
            Map<String, Object> metadata = new HashMap<>(chunk.getMetadata());
            metadata.put("documentId", documentId);
            metadata.put("fileName", safeFileName);
            metadata.put("contentType", file.getContentType());
            chunk.getMetadata().clear();
            chunk.getMetadata().putAll(metadata);
        }
        if (!chunks.isEmpty()) {
            vectorStore.add(chunks);
        }
        return chunks.size();
    }
}

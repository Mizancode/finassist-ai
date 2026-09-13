package com.finassist.document_service.service;

import com.finassist.document_service.dto.DocumentResponse;
import com.finassist.document_service.entity.FinancialDocument;
import com.finassist.document_service.exception.ResourceNotFoundException;
import com.finassist.document_service.repository.FinancialDocumentRepository;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DocumentService {
    private final FinancialDocumentRepository repository;
    private final PdfProcessingService pdfProcessingService;
    private final TextChunkingService textChunkingService;
    private final VectorStore vectorStore;
    private final VectorDocumentService vectorDocumentService;

    public DocumentService(FinancialDocumentRepository repository, PdfProcessingService pdfProcessingService, TextChunkingService textChunkingService, VectorStore vectorStore, VectorDocumentService vectorDocumentService) {
        this.repository = repository;
        this.pdfProcessingService = pdfProcessingService;
        this.textChunkingService = textChunkingService;
        this.vectorStore = vectorStore;
        this.vectorDocumentService = vectorDocumentService;
    }

    @Transactional
    public DocumentResponse uploadDocument(MultipartFile file) throws IOException {
        validateFile(file);
        System.out.println(
                "Processing document: "
                        + file.getOriginalFilename()
        );
        String extractedText=pdfProcessingService.extractText(file);
        if(extractedText==null || extractedText.isBlank()){
            throw new BadRequestException("No readable text found in PDF");
        }
        List<String> chunks=textChunkingService.splitText(extractedText);
        if(chunks.isEmpty()){
            throw new BadRequestException("Unable to create document Chunks");
        }
        FinancialDocument financialDocument=new FinancialDocument();

        financialDocument.setChunkCount(chunks.size());
        financialDocument.setContentType(file.getContentType());
        financialDocument.setFileName(file.getOriginalFilename());
        financialDocument.setStatus("PENDING");
        financialDocument.setFileSize(file.getSize());
        financialDocument.setUpdatedAt(LocalDateTime.now());
        FinancialDocument savedDocument=repository.save(financialDocument);
        int chunkCount =
                vectorDocumentService.processAndStore(
                        file,
                        savedDocument.getId()
                );
        List<Document> vectorDocuments =
                createVectorDocuments(
                        chunks,
                        savedDocument
                );
        vectorStore.add(vectorDocuments);
        savedDocument.setChunkCount(chunkCount);
        savedDocument.setStatus("COMPLETED");
        repository.save(savedDocument);
        System.out.println(
                "Document processed successfully. "
                        + "Chunks: "
                        + chunks.size()
        );
        return mapToResponse(savedDocument);

    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {

            throw new IllegalArgumentException(
                    "PDF file must not be empty"
            );
        }
        String fileName =
                file.getOriginalFilename();

        if (fileName == null
                || !fileName.toLowerCase()
                .endsWith(".pdf")) {

            throw new IllegalArgumentException(
                    "Only PDF files are supported"
            );
        }
    }

    private DocumentResponse mapToResponse(FinancialDocument savedDocument) {
        DocumentResponse response=new DocumentResponse();
        response.setChunkCount(savedDocument.getChunkCount());
        response.setContentType(savedDocument.getContentType());
        response.setFileName(savedDocument.getFileName());
        response.setFileSize(savedDocument.getFileSize());
        response.setId(savedDocument.getId());
        response.setStatus(savedDocument.getStatus());
        response.setUpdatedAt(savedDocument.getUpdatedAt());
        return response;
    }

    private List<Document> createVectorDocuments(List<String> chunks, FinancialDocument savedDocument) {
        List<Document> vectorDocuments =
                new ArrayList<>();

        for (int i = 0; i < chunks.size(); i++) {

            Map<String, Object> metadata =
                    new HashMap<>();

            metadata.put(
                    "documentId",
                    savedDocument.getId()
            );

            metadata.put(
                    "fileName",
                    savedDocument.getFileName()
            );

            metadata.put(
                    "chunkIndex",
                    i
            );

            metadata.put(
                    "documentType",
                    "FINANCIAL_PDF"
            );

            Document vectorDocument =
                    new Document(
                            chunks.get(i),
                            metadata
                    );

            vectorDocuments.add(vectorDocument);
        }
        return vectorDocuments;
    }

    public List<DocumentResponse> getAllDocuments() {
        List<FinancialDocument> list=repository.findAll();
        return list.stream()
                .map(this::mapToResponse)
        .toList();
    }

    public DocumentResponse getDocumentById(Long documentId) {
        FinancialDocument financialDocument=repository.findById(documentId).orElseThrow(()->new ResourceNotFoundException("Resource is not found with Id: "+documentId));
        return mapToResponse(financialDocument);
    }

    public String deleteDocumentById(Long documentId) {
        FinancialDocument financialDocument=repository.findById(documentId).orElseThrow(()->new ResourceNotFoundException("Resource is not found with Id: "+documentId));
        repository.delete(financialDocument);
        return "Document Deleted with ID: "+documentId;
    }
}

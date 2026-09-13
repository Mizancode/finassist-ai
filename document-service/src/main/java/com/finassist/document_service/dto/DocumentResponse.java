package com.finassist.document_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DocumentResponse {

    private Long id;
    private String fileName;
    private String contentType;
    private Long fileSize;
    private Integer chunkCount;
    private String status;
    private LocalDateTime updatedAt;
}

package com.finassist.document_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "financial_documents")
@Data
public class FinancialDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String fileName;
    @Column(nullable = false)
    private String contentType;
    @Column(nullable = false)
    private Long fileSize;
    @Column(nullable = false)
    private Integer chunkCount;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}

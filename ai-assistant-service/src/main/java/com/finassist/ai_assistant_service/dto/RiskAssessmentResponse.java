package com.finassist.ai_assistant_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RiskAssessmentResponse {

    private Long id;
    private Long customerId;
    private Long transactionId;
    private String transactionRef;
    private Integer riskScore;
    private String riskLevel;
    private String reasons;
    private String recommendation;
    private LocalDateTime createdAt;
}

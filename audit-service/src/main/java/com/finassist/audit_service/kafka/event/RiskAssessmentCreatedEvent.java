package com.finassist.audit_service.kafka.event;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RiskAssessmentCreatedEvent {
    private Long riskAssessmentId;
    private Long customerId;
    private Long transactionId;
    private String transactionRef;
    private Integer riskScore;
    private String riskLevel;
    private String reasons;
    private String recommendation;
    private LocalDateTime createdAt;
}

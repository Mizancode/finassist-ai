package com.finassist.audit_service.kafka.event;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AiRiskAnalysisEvent {
    private Long customerId;
    private String riskLevel;
    private Integer riskScore;
    private String summary;
    private LocalDateTime analyzedAt;
}

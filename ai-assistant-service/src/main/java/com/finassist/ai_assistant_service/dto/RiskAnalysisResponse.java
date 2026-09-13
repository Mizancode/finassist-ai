package com.finassist.ai_assistant_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class RiskAnalysisResponse {

    private Long customerId;

    private String riskLevel;

    private Integer riskScore;

    private String summary;

    private List<String> keyFindings;

    private List<String> transactionPatterns;

    private List<String> policyFindings;

    private List<String> recommendedActions;

}

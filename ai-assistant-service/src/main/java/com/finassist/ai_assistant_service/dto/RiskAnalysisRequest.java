package com.finassist.ai_assistant_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RiskAnalysisRequest {

    @NotNull(message = "Customer ID is required")
    private Long customerId;
}

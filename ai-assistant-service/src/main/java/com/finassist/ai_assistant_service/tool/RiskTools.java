package com.finassist.ai_assistant_service.tool;

import com.finassist.ai_assistant_service.dto.RiskAssessmentResponse;
import com.finassist.ai_assistant_service.service.MicroserviceClient;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class RiskTools {
    private final MicroserviceClient microserviceClient;

    public RiskTools(MicroserviceClient microserviceClient) {
        this.microserviceClient = microserviceClient;
    }

    @Tool(
            name = "getCustomerRiskAssessment",
            description = """
        Retrieve the current risk assessment of a financial customer.
        Use this when risk level, risk score, risk reason,
        fraud risk or compliance risk information is required.
        """
    )
    public RiskAssessmentResponse getCustomerRiskAssessment(Long customerId) {

        return microserviceClient.getCustomerRiskAssessment(customerId);
    }
}

package com.finassist.ai_assistant_service.tool;

import com.finassist.ai_assistant_service.dto.CustomerResponse;
import com.finassist.ai_assistant_service.service.MicroserviceClient;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class CustomerTools {
    private final MicroserviceClient microserviceClient;

    public CustomerTools(MicroserviceClient microserviceClient) {
        this.microserviceClient = microserviceClient;
    }

    @Tool(
            name = "getCustomerProfile",
            description = """
        Retrieve the profile information of a financial customer.
        Use this when customer identity, account type or account status
        is required for financial risk analysis.
        """
    )
    public CustomerResponse getCustomerProfile(Long customerId) {

        return microserviceClient.getCustomerProfile(customerId);
    }
}

package com.finassist.ai_assistant_service.tool;

import com.finassist.ai_assistant_service.dto.TransactionResponse;
import com.finassist.ai_assistant_service.service.MicroserviceClient;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionTools {
    private final MicroserviceClient microserviceClient;

    public TransactionTools(MicroserviceClient microserviceClient) {
        this.microserviceClient = microserviceClient;
    }

    @Tool(
            name = "getCustomerTransactions",
            description = """
        Retrieve the financial transactions of a customer.
        Use this for transaction history, transaction amounts,
        transaction patterns, unusually large transactions,
        suspicious activity and financial risk analysis.
        """
    )
    public List<TransactionResponse> getCustomerTransactions(Long customerId) {

        return microserviceClient.getCustomerTransactions(customerId);
    }
}

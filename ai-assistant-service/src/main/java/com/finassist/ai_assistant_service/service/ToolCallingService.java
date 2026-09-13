package com.finassist.ai_assistant_service.service;

import com.finassist.ai_assistant_service.tool.CustomerTools;
import com.finassist.ai_assistant_service.tool.RiskTools;
import com.finassist.ai_assistant_service.tool.TransactionTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ToolCallingService {
    private final ChatClient chatClient;

    private final CustomerTools customerTools;
    private final TransactionTools transactionTools;
    private final RiskTools riskTools;

    public ToolCallingService(
            ChatClient.Builder chatClientBuilder,
            CustomerTools customerTools,
            TransactionTools transactionTools,
            RiskTools riskTools) {

        this.chatClient = chatClientBuilder.build();

        this.customerTools = customerTools;
        this.transactionTools = transactionTools;
        this.riskTools = riskTools;
    }

    public String ask(String question) {

        return chatClient.prompt()
                .system("""
                        You are FinAssist AI, an enterprise financial intelligence assistant.

                        You have access to tools that retrieve real-time information
                        from the FinAssist microservices.

                        Use the available tools whenever the user's question requires
                        customer, transaction, or risk information.

                        Rules:

                        1. Never invent customer information.
                        2. Never invent transaction information.
                        3. Never invent risk scores or risk levels.
                        4. Use tools when real customer data is required.
                        5. Explain the retrieved information clearly.
                        6. If the requested customer does not exist, clearly state that.
                        7. Do not expose internal implementation details of the tools.
                        8. Do not claim that a tool was called unless necessary.
                        9. Distinguish between retrieved facts and your analysis.
                        10. Do not provide financial advice as a certainty.
                        """)
                .user(question)
                .tools(
                        customerTools,
                        transactionTools,
                        riskTools
                )
                .call()
                .content();
    }
}

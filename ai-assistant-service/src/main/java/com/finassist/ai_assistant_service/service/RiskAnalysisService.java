package com.finassist.ai_assistant_service.service;

import com.finassist.ai_assistant_service.dto.CustomerResponse;
import com.finassist.ai_assistant_service.dto.RiskAnalysisResponse;
import com.finassist.ai_assistant_service.dto.RiskAssessmentResponse;
import com.finassist.ai_assistant_service.dto.TransactionResponse;
import com.finassist.ai_assistant_service.event.AiRiskAnalysisEvent;
import com.finassist.ai_assistant_service.tool.CustomerTools;
import com.finassist.ai_assistant_service.tool.RiskTools;
import com.finassist.ai_assistant_service.tool.TransactionTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RiskAnalysisService {
    private static final String AI_RISK_TOPIC =
            "finassist.ai.risk.analysis";

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    private final CustomerTools customerTools;
    private final TransactionTools transactionTools;
    private final RiskTools riskTools;
    private final KafkaTemplate<String, AiRiskAnalysisEvent> kafkaTemplate;

    public RiskAnalysisService(ChatClient chatClient, VectorStore vectorStore, CustomerTools customerTools, TransactionTools transactionTools, RiskTools riskTools, KafkaTemplate<String, AiRiskAnalysisEvent> kafkaTemplate) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
        this.customerTools = customerTools;
        this.transactionTools = transactionTools;
        this.riskTools = riskTools;
        this.kafkaTemplate = kafkaTemplate;
    }

    public RiskAnalysisResponse analyze(Long customerId) {

        CustomerResponse customer =
                customerTools.getCustomerProfile(customerId);

        List<TransactionResponse> transactions =
                transactionTools.getCustomerTransactions(customerId);

        RiskAssessmentResponse currentRisk =
                riskTools.getCustomerRiskAssessment(customerId);

        String policyContext = retrieveRiskPolicies(
                customer,
                transactions,
                currentRisk
        );
        String transactionData = transactions.stream()
                .map(transaction -> """
                        Transaction ID: %s
                        Amount: %s
                        Type: %s
                        Status: %s
                        Category: %s
                        Date: %s
                        """.formatted(
                        transaction.getId(),
                        transaction.getAmount(),
                        transaction.getTransactionType(),
                        transaction.getStatus(),
                        transaction.getTransactionType(),
                        transaction.getCreatedAt()
                ))
                .collect(Collectors.joining("\n---\n"));
        String prompt = """
                Analyze the financial risk of the following customer.

                CUSTOMER INFORMATION
                --------------------
                Customer ID: %s
                Name: %s
                Account Type: %s
                Status: %s

                CURRENT RISK ASSESSMENT
                ----------------------
                Risk Level: %s
                Risk Score: %s
                Reason: %s
                Status: %s

                TRANSACTIONS
                ------------
                %s

                FINANCIAL POLICY CONTEXT
                ------------------------
                %s

                Return the result as JSON with exactly these fields:

                {
                  "riskLevel": "LOW | MEDIUM | HIGH | CRITICAL",
                  "riskScore": 0,
                  "summary": "short explanation",
                  "keyFindings": ["finding 1", "finding 2"],
                  "transactionPatterns": ["pattern 1", "pattern 2"],
                  "policyFindings": ["policy finding 1"],
                  "recommendedActions": ["action 1", "action 2"]
                }

                Rules:

                1. Use the supplied customer and transaction data.
                2. Use the supplied risk assessment.
                3. Use the financial policy context when identifying policy concerns.
                4. Do not invent transactions.
                5. Do not invent customer information.
                6. Do not invent policies.
                7. Keep the risk score between 0 and 100.
                8. Clearly distinguish facts from analysis.
                9. Recommendations must be reasonable risk/compliance actions.
                10. Return valid JSON only.
                """.formatted(
                customer.getId(),
                customer.getFullName(),
                customer.getAccountType(),
                "ACTIVE",
                currentRisk.getRiskLevel(),
                currentRisk.getRiskScore(),
                currentRisk.getReasons(),
                "COMPLETED",
                transactionData,
                policyContext
        );
        RiskAnalysisResponse response = chatClient.prompt()
                .system("""
                        You are FinAssist AI's financial risk analysis engine.

                        Your job is to analyze customer financial activity
                        using only the supplied information.

                        You are not a financial advisor.
                        Do not fabricate facts.
                        """)
                .user(prompt)
                .call()
                .entity(RiskAnalysisResponse.class);

        publishRiskAnalysisEvent(response);

        return response;

    }

    private String retrieveRiskPolicies(
            CustomerResponse customer,
            List<TransactionResponse> transactions,
            RiskAssessmentResponse currentRisk) {

        String query = """
                financial risk policy high value transactions
                customer transaction monitoring
                suspicious financial activity
                compliance risk assessment
                """;

        SearchRequest searchRequest = SearchRequest.builder()
                .query(query)
                .topK(5)
                .similarityThreshold(0.50)
                .build();

        List<Document> documents =
                vectorStore.similaritySearch(searchRequest);

        if (documents == null || documents.isEmpty()) {
            return "No relevant financial policy documents were found.";
        }

        return documents.stream()
                .map(Document::getText)
                .collect(Collectors.joining(
                        "\n\n--- POLICY DOCUMENT CHUNK ---\n\n"
                ));
    }


    private void publishRiskAnalysisEvent(
            RiskAnalysisResponse response) {

        AiRiskAnalysisEvent event = new AiRiskAnalysisEvent();

        event.setCustomerId(response.getCustomerId());
        event.setRiskLevel(response.getRiskLevel());
        event.setRiskScore(response.getRiskScore());
        event.setSummary(response.getSummary());
        event.setAnalyzedAt(LocalDateTime.now());

        kafkaTemplate.send(
                AI_RISK_TOPIC,
                String.valueOf(response.getCustomerId()),
                event
        );
    }

}


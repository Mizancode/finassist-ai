package com.finassist.ai_assistant_service.service;

import com.finassist.ai_assistant_service.dto.CustomerResponse;
import com.finassist.ai_assistant_service.dto.RiskAssessmentResponse;
import com.finassist.ai_assistant_service.dto.TransactionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Service
public class MicroserviceClient {
    private final RestClient transactionClient;
    private final RestClient riskClient;

    public MicroserviceClient(
            @Value("${finassist.services.transaction.base-url}") String transactionBaseUrl,
            @Value("${finassist.services.risk.base-url}") String riskBaseUrl) {

        this.transactionClient = RestClient.builder()
                .baseUrl(transactionBaseUrl)
                .build();

        this.riskClient = RestClient.builder()
                .baseUrl(riskBaseUrl)
                .build();
    }

    public CustomerResponse getCustomerProfile(Long customerId) {

        return transactionClient.get()
                .uri("/api/customer/{customerId}", customerId)
                .retrieve()
                .body(CustomerResponse.class);
    }

    public List<TransactionResponse> getCustomerTransactions(Long customerId) {

        TransactionResponse[] response = transactionClient.get()
                .uri("/api/transaction/transactions/{customerId}", customerId)
                .retrieve()
                .body(TransactionResponse[].class);

        if (response == null) {
            return List.of();
        }

        return Arrays.asList(response);
    }

    public RiskAssessmentResponse getCustomerRiskAssessment(Long customerId) {

        RiskAssessmentResponse response=riskClient.get()
                .uri("/api/risk/customer/{customerId}", customerId)
                .retrieve()
                .body(RiskAssessmentResponse.class);
        if (response == null) {
            throw new RuntimeException("Resource not Found...");
        }
        return response;
    }
}

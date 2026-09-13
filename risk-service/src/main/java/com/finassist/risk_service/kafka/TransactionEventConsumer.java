package com.finassist.risk_service.kafka;

import com.finassist.risk_service.kafka.event.TransactionEvent;
import com.finassist.risk_service.service.RiskAnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TransactionEventConsumer {

    @Autowired
    private RiskAnalysisService riskAnalysisService;

    @KafkaListener(topics = "finassist.transaction.created",groupId = "finassist-risk-group",
            containerFactory =
                    "transactionKafkaListenerContainerFactory")
    public void consumerTransactionCreated(TransactionEvent event){
        System.out.println(
                "=========================================="
        );

        System.out.println(
                "Transaction event received by Risk Service"
        );

        System.out.println(
                "Transaction Ref: "
                        + event.getTransactionRef()
        );

        System.out.println(
                "Customer ID: "
                        + event.getCustomerId()
        );

        System.out.println(
                "Amount: "
                        + event.getAmount()
        );

        System.out.println(
                "=========================================="
        );
        log.info("Kafka Listener Listen event in risk-service from transaction service");
        riskAnalysisService.analyzeAssessment(event);
    }
}

package com.finassist.audit_service.kafka;

import com.finassist.audit_service.kafka.event.RiskAssessmentCreatedEvent;
import com.finassist.audit_service.service.AuditService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuditEventConsumer {

    @Autowired
    private AuditService auditService;

    @KafkaListener(topics = "finassist.rist.assessment.created",groupId = "finassist-audit-group",
            containerFactory =
                    "riskKafkaListenerContainerFactory")
    public void consumeRiskAssessment(RiskAssessmentCreatedEvent event){
        System.out.println(
                "=========================================="
        );

        System.out.println(
                "Risk assessmenct event received"
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
                "Risk Score: "
                        + event.getRiskScore()
        );

        System.out.println(
                "Risk Level: "
                        + event.getRiskLevel()
        );

        System.out.println(
                "=========================================="
        );
        log.info("Kafka Listener Listen event in Audit Service from risk service");
        auditService.recordRiskAssessment(event);
    }
}

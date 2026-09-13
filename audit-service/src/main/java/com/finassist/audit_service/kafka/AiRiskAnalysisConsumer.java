package com.finassist.audit_service.kafka;

import com.finassist.audit_service.kafka.event.AiRiskAnalysisEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AiRiskAnalysisConsumer {

    @KafkaListener(
            topics = "finassist.ai.risk.analysis",
            groupId = "audit-service-ai-risk-group",
            containerFactory =
                    "aiRiskKafkaListenerContainerFactory"
    )
    public void consume(AiRiskAnalysisEvent event) {

        System.out.println(
                "AI Risk Analysis Audit Event Received: " + event
        );
    }
}

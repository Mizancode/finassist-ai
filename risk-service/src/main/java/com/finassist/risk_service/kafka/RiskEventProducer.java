package com.finassist.risk_service.kafka;

import com.finassist.risk_service.entity.RiskAssessment;
import com.finassist.risk_service.kafka.event.RiskAssessmentCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RiskEventProducer {
    public static final String TOPIC="finassist.rist.assessment.created";
    private final KafkaTemplate<String, RiskAssessmentCreatedEvent> kafkaTemplate;

    public RiskEventProducer(KafkaTemplate<String, RiskAssessmentCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishRiskAssessmentCreated(RiskAssessment riskAssessment){
        RiskAssessmentCreatedEvent event=new RiskAssessmentCreatedEvent();
        event.setRiskAssessmentId(riskAssessment.getId());
        event.setCreatedAt(riskAssessment.getCreatedAt());
        event.setCustomerId(riskAssessment.getCustomerId());
        event.setReasons(riskAssessment.getReasons());
        event.setRecommendation(riskAssessment.getRecommendation());
        event.setRiskLevel(riskAssessment.getRiskLevel());
        event.setRiskScore(riskAssessment.getRiskScore());
        event.setTransactionId(riskAssessment.getTransactionId());
        event.setTransactionRef(riskAssessment.getTransactionRef());
        kafkaTemplate.send(TOPIC,riskAssessment.getTransactionRef(),event);
        log.info("Risk Assessment Created Event Successfully");
    }
}

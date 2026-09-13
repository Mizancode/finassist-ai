package com.finassist.audit_service.service;

import com.finassist.audit_service.entity.Audit;
import com.finassist.audit_service.exception.ResourceNotFoundException;
import com.finassist.audit_service.kafka.event.RiskAssessmentCreatedEvent;
import com.finassist.audit_service.repository.AuditRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditService {

    @Autowired
    private AuditRepository auditRepository;

    @Transactional
    public void recordRiskAssessment(RiskAssessmentCreatedEvent event){
        Audit audit=new Audit();
        audit.setEventType("RISK_ASSESSMENT_CREATED");
        audit.setCustomerId(event.getCustomerId());
        audit.setDetails("Risk assessment created. "
                + "Reasons: "
                + event.getReasons()
                + ". Recommendation: "
                + event.getRecommendation());
        audit.setRiskLevel(event.getRiskLevel());
        audit.setRiskScore(event.getRiskScore());
        audit.setTransactionId(event.getTransactionId());
        audit.setTransactionRef(event.getTransactionRef());
        auditRepository.save(audit);
    }

    public List<Audit> getAllAuditByCustomerId(Long customerId) {
        return auditRepository.findAllAuditByCustomerId(customerId).orElseThrow(()-> new ResourceNotFoundException("Resource is not found with Customer ID :"+customerId));
    }

    public List<Audit> getAllAuditByTransactionRef(String transactionRef) {
        return auditRepository.findAllAuditByTransactionRef(transactionRef).orElseThrow(()-> new ResourceNotFoundException("Resource is not found with Transaction Reference :"+transactionRef));
    }
}

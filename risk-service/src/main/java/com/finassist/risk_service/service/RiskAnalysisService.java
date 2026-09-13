package com.finassist.risk_service.service;

import com.finassist.risk_service.dto.RiskAssessmentResponse;
import com.finassist.risk_service.dto.RiskResult;
import com.finassist.risk_service.entity.RiskAssessment;
import com.finassist.risk_service.exception.ResourceNotFoundException;
import com.finassist.risk_service.kafka.RiskEventProducer;
import com.finassist.risk_service.kafka.event.TransactionEvent;
import com.finassist.risk_service.repository.RiskAssessmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiskAnalysisService {

    @Autowired
    private RiskAssessmentRepository riskAssessmentRepository;
    @Autowired
    private RiskEventProducer riskEventProducer;
    @Autowired
    private RiskScoringEngine riskScoringEngine;

    @Transactional
    public RiskAssessmentResponse analyzeAssessment(TransactionEvent transactionEvent) {
        if(riskAssessmentRepository.existsByTransactionId(transactionEvent.getTransactionId())){
            return mapToResponse(riskAssessmentRepository.findByTransactionId(transactionEvent.getTransactionId()));
        }
        RiskResult result=riskScoringEngine.calculateRisk(transactionEvent);
        RiskAssessment riskAssessment=new RiskAssessment();
        riskAssessment.setCustomerId(transactionEvent.getCustomerId());
        riskAssessment.setTransactionId(transactionEvent.getTransactionId());
        riskAssessment.setTransactionRef(transactionEvent.getTransactionRef());
        riskAssessment.setReasons(result.getReasons());
        riskAssessment.setRecommendation(result.getRecommendation());
        riskAssessment.setRiskLevel(result.getLevel());
        riskAssessment.setRiskScore(result.getScore());
        RiskAssessment savedAssessment=riskAssessmentRepository.save(riskAssessment);
        riskEventProducer.publishRiskAssessmentCreated(savedAssessment);
        return mapToResponse(savedAssessment);
    }

    private RiskAssessmentResponse mapToResponse(RiskAssessment risk) {
        RiskAssessmentResponse response=new RiskAssessmentResponse();
        response.setId(risk.getId());
        response.setCreatedAt(risk.getCreatedAt());
        response.setCustomerId(risk.getCustomerId());
        response.setReasons(risk.getReasons());
        response.setRecommendation(risk.getRecommendation());
        response.setRiskLevel(risk.getRiskLevel());
        response.setRiskScore(risk.getRiskScore());
        response.setTransactionId(risk.getTransactionId());
        response.setTransactionRef(risk.getTransactionRef());
        return response;
    }

    public RiskAssessmentResponse getRiskAssessmentByCustomerId(Long customerId) {
        RiskAssessment riskAssessment=riskAssessmentRepository.findTopByCustomerIdOrderByCreatedAtDesc(customerId).orElseThrow(()->new ResourceNotFoundException("Resource is not found by Customer Id with Id: "+customerId));
        return mapToResponse(riskAssessment);
    }

    public List<RiskAssessmentResponse> getRiskAssessmentsByCustomerId(Long customerId) {
        List<RiskAssessment> list=riskAssessmentRepository.findByCustomerIdOrderByCreatedAtDesc(customerId).orElseThrow(()->new ResourceNotFoundException("Resources is not found by Customer Id with Id: "+customerId));
        return list
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}

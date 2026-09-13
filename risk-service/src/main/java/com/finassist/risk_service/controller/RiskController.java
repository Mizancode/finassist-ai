package com.finassist.risk_service.controller;

import com.finassist.risk_service.dto.RiskAssessmentResponse;
import com.finassist.risk_service.kafka.event.TransactionEvent;
import com.finassist.risk_service.service.RiskAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/risk")
public class RiskController {

    @Autowired
    private RiskAnalysisService riskAnalysisService;

    @PostMapping
    public ResponseEntity<RiskAssessmentResponse> analyzeAssessment(@RequestBody TransactionEvent transactionEvent){
        return ResponseEntity.ok(riskAnalysisService.analyzeAssessment(transactionEvent));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<RiskAssessmentResponse> getRiskAssessmentByCustomerId(@PathVariable Long customerId){
        return ResponseEntity.ok(riskAnalysisService.getRiskAssessmentByCustomerId(customerId));
    }

    @GetMapping("/customer/{customerId}/history")
    public ResponseEntity<List<RiskAssessmentResponse>> getRiskAssessmentsByCustomerId(@PathVariable Long customerId){
        return ResponseEntity.ok(riskAnalysisService.getRiskAssessmentsByCustomerId(customerId));
    }
}

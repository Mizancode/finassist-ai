package com.finassist.ai_assistant_service.controller;

import com.finassist.ai_assistant_service.dto.RiskAnalysisRequest;
import com.finassist.ai_assistant_service.dto.RiskAnalysisResponse;
import com.finassist.ai_assistant_service.service.RiskAnalysisService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class RiskAnalysisController {

    @Autowired
    private RiskAnalysisService riskAnalysisService;

    @PostMapping("/risk-analysis")
    public ResponseEntity<RiskAnalysisResponse> analyzeRisk(
            @Valid @RequestBody RiskAnalysisRequest request) {

        RiskAnalysisResponse response =
                riskAnalysisService.analyze(request.getCustomerId());

        return ResponseEntity.ok(response);
    }

}

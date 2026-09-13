package com.finassist.risk_service.service;

import com.finassist.risk_service.dto.RiskResult;
import com.finassist.risk_service.kafka.event.TransactionEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class RiskScoringEngine {
    public RiskResult calculateRisk(TransactionEvent transaction) {
        int score = 0;
        List<String> reasons = new ArrayList<>();

        // =====================================================
        // RULE 1: HIGH TRANSACTION AMOUNT
        // =====================================================

        if (transaction.getAmount().compareTo(new BigDecimal("100000")) >= 0) {
            score += 40;
            reasons.add("Transaction amount is usually high");
        } else if (transaction.getAmount().compareTo(new BigDecimal("50000")) >= 0) {
            score += 25;
            reasons.add("Transaction amount exceeds normal Threshold");
        }

        // =====================================================
        // RULE 2: HIGH-RISK COUNTRY
        // =====================================================

        if (transaction.getCountry() != null) {
            String country = transaction.getCountry().toUpperCase();
            if (country.equals("XX") || country.equals("YY")) {
                score += 30;
                reasons.add("Transaction originates from High Risk Country");
            }
        }

        // =====================================================
        // RULE 3: INTERNATIONAL TRANSACTION
        // =====================================================

        if (transaction.getCurrency() != null && !transaction.getCurrency().equalsIgnoreCase("INR")) {
            score += 15;
            reasons.add("Transaction uses a foreign currency");
        }

        // =====================================================
        // RULE 4: CASH TRANSACTION
        // =====================================================

        if (transaction.getTransactionType() != null && transaction.getTransactionType().equalsIgnoreCase("CASH_WITHDRAWAL")) {
            score += 15;
            reasons.add("Cash withdrawal requires additional monitoring");
        }

        // =====================================================
        // LIMIT SCORE
        // =====================================================

        score = Math.min(score, 100);
        String riskLevel;
        if (score >= 70) {
            riskLevel = "HIGH";
        } else if (score >= 40) {
            riskLevel = "MEDIUM";
        } else {
            riskLevel = "LOW";
        }
        if (reasons.isEmpty()) {
            reasons.add("No significant risk indicators detected");
        }
        String recommendation;
        switch (riskLevel) {
            case "HIGH" -> recommendation = "Flag transaction for manual review";
            case "MEDIUM" -> recommendation = "Monitor transaction and perform additional verification";
            default -> recommendation = "Allow transaction with standard monitoring";
        }
        RiskResult riskResult=new RiskResult();
        riskResult.setLevel(riskLevel);
        riskResult.setReasons(String.join("; ", reasons));
        riskResult.setRecommendation(recommendation);
        riskResult.setScore(score);
        return riskResult;
    }
}

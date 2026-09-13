package com.finassist.risk_service.dto;

import lombok.Data;

@Data
public class RiskResult {
    private int score;
    private String level;
    private String reasons;
    private String recommendation;
}

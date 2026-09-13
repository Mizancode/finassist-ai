package com.finassist.ai_assistant_service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionResponse {

    private Long id;
    private String transactionRef;
    private Long customerId;
    private BigDecimal amount;
    private String currency;
    private String transactionType;
    private String merchant;
    private String country;
    private String status;
    private LocalDateTime createdAt;
}

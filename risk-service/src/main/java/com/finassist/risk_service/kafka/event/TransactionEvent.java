package com.finassist.risk_service.kafka.event;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionEvent {
    private Long transactionId;
    private String transactionRef;
    private Long customerId;
    private BigDecimal amount;
    private String currency;
    private String transactionType;
    private String merchant;
    private String country;
    private LocalDateTime createdAt;
}

package com.finassist.transaction_service.kafka.events;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionEventCreated {
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

package com.finassist.transaction_service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {
    @NotBlank(message = "CustomerID is required")
    private Long customerId;
    @NotBlank(message = "Amount is required")
    @DecimalMin(
            value = "0.01",
            message = "Amount must be greater than zero"
    )
    private BigDecimal amount;
    @NotBlank(message = "Currency is required")
    @Size(min = 3, max = 3, message = "Currency must be 3 characters")
    private String currency;
    @NotBlank(message = "TransactionType is Required")
    private String transactionType;
    @Size(max = 150, message = "Merchant name is too long")
    private String merchant;
    @Size(max = 80, message = "Country name is too long")
    private String country;
}

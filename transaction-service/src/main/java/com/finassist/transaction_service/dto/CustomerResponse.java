package com.finassist.transaction_service.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
public class CustomerResponse {

    private Long id;
    private String customerCode;
    private String fullName;
    private String email;
    private String accountType;
    LocalDateTime createdAt;
}

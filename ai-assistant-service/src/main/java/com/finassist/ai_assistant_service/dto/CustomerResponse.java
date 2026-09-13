package com.finassist.ai_assistant_service.dto;

import lombok.Data;

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

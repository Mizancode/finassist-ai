package com.finassist.ai_assistant_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatResponse {

    private String conversationId;
    private String response;
    private String model;
    private LocalDateTime timestamp;
}

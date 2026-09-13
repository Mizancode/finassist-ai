package com.finassist.ai_assistant_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ToolChatRequest {
    @NotBlank(message = "Message is required")
    private String message;
}

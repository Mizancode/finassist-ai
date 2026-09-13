package com.finassist.ai_assistant_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChatRequest {
    @NotBlank(message = "Message must not be blank")
    @Size(min = 2, max = 2000, message = "Message must contain between 2 and 2000 characters")
    private String message;

    @Size(max = 100, message = "Conversation ID must not exceed 100 characters")
    private String conversationId;
}

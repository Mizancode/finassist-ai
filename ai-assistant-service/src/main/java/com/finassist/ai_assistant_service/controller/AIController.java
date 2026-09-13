package com.finassist.ai_assistant_service.controller;

import com.finassist.ai_assistant_service.dto.ChatRequest;
import com.finassist.ai_assistant_service.dto.ChatResponse;
import com.finassist.ai_assistant_service.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    @Autowired
    private AIService aiService;

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> generateChat(@RequestBody ChatRequest chatRequest){
        return ResponseEntity.ok(aiService.generateChat(chatRequest));
    }

}

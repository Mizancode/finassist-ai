package com.finassist.ai_assistant_service.controller;

import com.finassist.ai_assistant_service.dto.ToolChatRequest;
import com.finassist.ai_assistant_service.dto.ToolChatResponse;
import com.finassist.ai_assistant_service.service.ToolCallingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class ToolCallingController {

    @Autowired
    private ToolCallingService toolCallingService;


    @PostMapping("/tools")
    public ResponseEntity<ToolChatResponse> askWithTools(
            @Valid @RequestBody ToolChatRequest request) {

        String answer = toolCallingService.ask(request.getMessage());
        ToolChatResponse response=new ToolChatResponse();
        response.setAnswer(answer);
        return ResponseEntity.ok(response);
    }
}

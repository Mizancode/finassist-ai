package com.finassist.ai_assistant_service.controller;

import com.finassist.ai_assistant_service.dto.ChatRequest;
import com.finassist.ai_assistant_service.dto.RagChatResponse;
import com.finassist.ai_assistant_service.dto.RagSearchResult;
import com.finassist.ai_assistant_service.service.RagService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ai")
public class RagController {

    @Autowired
    private RagService ragService;

    @PostMapping("/rag")
    public ResponseEntity<RagChatResponse> askQuestion(
            @Valid @RequestBody ChatRequest request) {

        RagSearchResult result = ragService.ask(request.getMessage());

        List<RagChatResponse.Source> sources = result.getSources()
                .stream()
                .map(source -> new RagChatResponse.Source(
                        source.getFileName(),
                        source.getDocumentId()
                ))
                .toList();

        RagChatResponse response = new RagChatResponse();
        response.setAnswer(result.getAnswer());
        response.setSources(sources);

        return ResponseEntity.ok(response);
    }
}

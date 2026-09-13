package com.finassist.ai_assistant_service.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder){
        return builder
                .defaultOptions(OllamaChatOptions.builder()
                        .model("qwen3")
                        .temperature(0.3)
                        .disableThinking()
                        .maxTokens(500)
                        )
                .build();
    }
}

package com.finassist.ai_assistant_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class AiAssistantServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiAssistantServiceApplication.class, args);
	}

}

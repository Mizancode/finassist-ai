package com.finassist.risk_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class RiskServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RiskServiceApplication.class, args);
	}

}

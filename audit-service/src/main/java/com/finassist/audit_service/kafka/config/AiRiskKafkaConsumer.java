package com.finassist.audit_service.kafka.config;

import com.finassist.audit_service.kafka.event.AiRiskAnalysisEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AiRiskKafkaConsumer {
    private static final String BOOTSTRAP_SERVERS = "kafka:9092";
    @Bean
    public ConsumerFactory<String, AiRiskAnalysisEvent> aiRiskEventConsumerFactory() {
        JacksonJsonDeserializer<AiRiskAnalysisEvent> deserializer = new JacksonJsonDeserializer<>(AiRiskAnalysisEvent.class);
        deserializer.addTrustedPackages("com.finassist.audit.kafka");
        deserializer.setUseTypeHeaders(false);
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "audit-service-ai-risk-group");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AiRiskAnalysisEvent> aiRiskKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AiRiskAnalysisEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(aiRiskEventConsumerFactory());
        return factory;
    }
}

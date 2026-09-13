package com.finassist.transaction_service.kafka.events.component;

import com.finassist.transaction_service.entity.Transaction;
import com.finassist.transaction_service.kafka.events.TransactionEventCreated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TransactionEventProducer {
    public static final String TOPIC="finassist.transaction.created";
    private final KafkaTemplate<String, TransactionEventCreated> kafkaTemplate;

    public TransactionEventProducer(KafkaTemplate<String, TransactionEventCreated> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishTransactionCreated(Transaction transaction){
        TransactionEventCreated transactionEventCreated=new TransactionEventCreated();
        transactionEventCreated.setCustomerId(transaction.getCustomerId());
        transactionEventCreated.setTransactionId(transaction.getId());
        transactionEventCreated.setAmount(transaction.getAmount());
        transactionEventCreated.setCountry(transaction.getCountry());
        transactionEventCreated.setCreatedAt(transaction.getCreatedAt());
        transactionEventCreated.setCurrency(transaction.getCurrency());
        transactionEventCreated.setMerchant(transaction.getMerchant());
        transactionEventCreated.setTransactionRef(transaction.getTransactionRef());
        transactionEventCreated.setTransactionType(transaction.getTransactionType());
        kafkaTemplate.send(TOPIC,transaction.getTransactionRef(),transactionEventCreated);
        log.info("Kafka Event Created Successfully");
    }
}

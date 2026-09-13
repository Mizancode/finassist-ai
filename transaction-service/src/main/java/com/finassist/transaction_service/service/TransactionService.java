package com.finassist.transaction_service.service;

import com.finassist.transaction_service.dto.TransactionRequest;
import com.finassist.transaction_service.dto.TransactionResponse;
import com.finassist.transaction_service.entity.Transaction;
import com.finassist.transaction_service.exception.ResourceNotFoundException;
import com.finassist.transaction_service.kafka.events.component.TransactionEventProducer;
import com.finassist.transaction_service.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private TransactionEventProducer transactionEventProducer;

    @Transactional
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {
          customerService.getCustomerEntityById(transactionRequest.getCustomerId());
          Transaction transaction=new Transaction();
          transaction.setTransactionRef(generateTransactionRef());
          transaction.setAmount(transactionRequest.getAmount());
          transaction.setCountry(transactionRequest.getCountry());
          transaction.setCurrency(transactionRequest.getCurrency());
          transaction.setCustomerId(transactionRequest.getCustomerId());
          transaction.setMerchant(transactionRequest.getMerchant());
          transaction.setTransactionType(transactionRequest.getTransactionType());
          transaction.setStatus("PENDING");
          Transaction savedTransaction=transactionRepository.save(transaction);
          transactionEventProducer.publishTransactionCreated(savedTransaction);
          return mapToResponse(savedTransaction);
    }

    private TransactionResponse mapToResponse(Transaction savedTransaction) {
        TransactionResponse transactionResponse=new TransactionResponse();
        transactionResponse.setId(savedTransaction.getId());
        transactionResponse.setAmount(savedTransaction.getAmount());
        transactionResponse.setCountry(savedTransaction.getCountry());
        transactionResponse.setCreatedAt(savedTransaction.getCreatedAt());
        transactionResponse.setCurrency(savedTransaction.getCurrency());
        transactionResponse.setCustomerId(savedTransaction.getCustomerId());
        transactionResponse.setMerchant(savedTransaction.getMerchant());
        transactionResponse.setStatus(savedTransaction.getStatus());
        transactionResponse.setTransactionRef(savedTransaction.getTransactionRef());
        transactionResponse.setTransactionType(savedTransaction.getTransactionType());
        return transactionResponse;
    }

    private String generateTransactionRef() {
        return "TXN-"+
                UUID.randomUUID()
                        .toString()
                        .substring(0,8)
                        .toUpperCase();
    }

    public TransactionResponse getTransactionById(Long transactionalId) {
        Transaction transaction=transactionRepository.findById(transactionalId).orElseThrow(()->new ResourceNotFoundException("Transaction is not found wiht ID: "+transactionalId));
        return mapToResponse(transaction);
    }

    public List<TransactionResponse> getTransactionsByCustomerId(Long customerId) {
        customerService.getCustomerEntityById(customerId);
        List<Transaction> transactions=transactionRepository.findByCustomerIdOrderByCreatedAtDesc(customerId);
        return transactions.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public boolean exists(long l) {
        return transactionRepository.existsById(l);
    }
}

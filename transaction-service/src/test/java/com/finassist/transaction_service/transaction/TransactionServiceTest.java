package com.finassist.transaction_service.transaction;

import com.finassist.transaction_service.dto.TransactionResponse;
import com.finassist.transaction_service.entity.Transaction;
import com.finassist.transaction_service.repository.TransactionRepository;
import com.finassist.transaction_service.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;
    @InjectMocks
    private TransactionService transactionService;

    @Test
    void checkIfTransactionIsExistByTransactionID(){
        Transaction transaction=new Transaction();
        transaction.setId(1L);
        transaction.setCustomerId(2L);
        transaction.setTransactionType("CARD_PAYMENT");
        transaction.setStatus("COMPLETED");
        transaction.setMerchant("Amazon");
        transaction.setCurrency("INR");
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setCountry("IN");
        transaction.setAmount(BigDecimal.valueOf(25000.00));
        transaction.setTransactionRef("TXN-03027899");
        Mockito.when(transactionRepository.findById(1L))
                .thenReturn(Optional.of(transaction));
        TransactionResponse result =
                transactionService.getTransactionById(1L);

        // Verify result
        assertEquals("COMPLETED", result.getStatus());

        // Verify repository was actually called
        Mockito.verify(transactionRepository).findById(1L);
    }
}

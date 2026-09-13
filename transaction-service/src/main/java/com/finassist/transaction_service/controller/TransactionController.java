package com.finassist.transaction_service.controller;

import com.finassist.transaction_service.dto.TransactionRequest;
import com.finassist.transaction_service.dto.TransactionResponse;
import com.finassist.transaction_service.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransactionRequest transactionRequest){
        return ResponseEntity.ok(transactionService.createTransaction(transactionRequest));
    }

    @GetMapping("/{transactionalId}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable Long transactionalId){
        return ResponseEntity.ok(transactionService.getTransactionById(transactionalId));
    }

    @GetMapping("/transactions/{customerId}")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByCustomerId(@PathVariable Long customerId){
        return ResponseEntity.ok(transactionService.getTransactionsByCustomerId(customerId));
    }

}

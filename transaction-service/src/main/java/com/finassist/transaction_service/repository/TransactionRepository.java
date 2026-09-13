package com.finassist.transaction_service.repository;

import com.finassist.transaction_service.dto.TransactionResponse;
import com.finassist.transaction_service.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByCustomerIdOrderByCreatedAtDesc(Long customerId);
}

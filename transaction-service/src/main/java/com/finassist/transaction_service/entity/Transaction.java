package com.finassist.transaction_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions",indexes = {
        @Index(name = "idx_transaction_customer", columnList = "customer_id"),
        @Index(name = "idx_transaction_created_at", columnList = "created_at")
})
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false,length = 50,name = "transaction_ref")
    private String transactionRef;
    @Column(nullable = false,name = "customer_id")
    private Long customerId;
    @Column(precision = 19, scale = 2,nullable = false)
    private BigDecimal amount;
    @Column(nullable = false,length = 3)
    private String currency;
    @Column(nullable = false,length = 30,name = "transaction_type")
    private String transactionType;
    @Column(length = 150)
    private String merchant;
    @Column(length = 80)
    private String country;
    @Column(nullable = false,length = 30)
    private String status;
    @CreationTimestamp
    private LocalDateTime createdAt;
}

package com.finassist.audit_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit", indexes = {
        @Index(name = "idx_audit_event_type", columnList = "event_type"),
        @Index(name = "idx_audit_customer", columnList = "customer_id")
})
@Data
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "event_type", nullable = false)
    private String eventType;
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "transaction_id")
    private Long transactionId;
    @Column(name = "transaction_ref")
    private String transactionRef;
    @Column(name = "risk_level")
    private String riskLevel;
    @Column(name = "risk_score")
    private Integer riskScore;
    @Column(columnDefinition = "TEXT")
    private String details;
    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}

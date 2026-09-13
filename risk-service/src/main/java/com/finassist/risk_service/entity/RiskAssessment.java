package com.finassist.risk_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "risk_assessments",indexes = @Index( name = "idx_risk_customer",
        columnList = "customer_id"))
@Data
public class RiskAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "customer_id",nullable = false)
    private Long customerId;
    @Column(name = "transaction_id",nullable = false)
    private Long transactionId;
    @Column(name = "transaction_ref",nullable = false)
    private String transactionRef;
    @Column(name = "risk_score",nullable = false)
    private Integer riskScore;
    @Column(name = "risk_level",nullable = false,length = 20)
    private String riskLevel;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String reasons;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String recommendation;
    @Column(nullable = false,name = "created}_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
}

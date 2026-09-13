package com.finassist.transaction_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "customers", indexes = {
        @Index(name = "idx_customer_code", columnList = "customer_code")
})
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "customer_code", unique = true,nullable = false,length = 50)
    private String customerCode;
    @Column(nullable = false,length = 100,name = "full_name")
    private String fullName;
    @Column(nullable = false,unique = true,length = 150)
    @Email(message = "Invalid Email")
    private String email;
    @Column(nullable = false,length = 30,name = "account_type")
    private String accountType;
    @CreationTimestamp
    LocalDateTime createdAt;
}

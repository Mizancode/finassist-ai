package com.finassist.risk_service.repository;

import com.finassist.risk_service.entity.RiskAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RiskAssessmentRepository extends JpaRepository<RiskAssessment,Long> {

    RiskAssessment findByTransactionId(Long transactionId);


    Optional<List<RiskAssessment>> findByCustomerIdOrderByCreatedAtDesc(Long customerId);

    boolean existsByTransactionId(Long transactionId);

    Optional<RiskAssessment> findTopByCustomerIdOrderByCreatedAtDesc(Long customerId);
}

package com.finassist.audit_service.repository;

import com.finassist.audit_service.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuditRepository extends JpaRepository<Audit,Long> {

    Optional<List<Audit>> findAllAuditByCustomerId(Long customerId);

    Optional<List<Audit>> findAllAuditByTransactionRef(String transactionRef);
}

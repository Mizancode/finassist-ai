package com.finassist.document_service.repository;

import com.finassist.document_service.entity.FinancialDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialDocumentRepository extends JpaRepository<FinancialDocument,Long> {
}

package com.finassist.audit_service.controller;

import com.finassist.audit_service.entity.Audit;
import com.finassist.audit_service.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Audit>> getAllAuditByCustomerId(@PathVariable Long customerId){
        return ResponseEntity.ok(auditService.getAllAuditByCustomerId(customerId));
    }

    @GetMapping("/transaction/{transactionRef}")
    public ResponseEntity<List<Audit>> getAllAuditByTransactionRef(@PathVariable String transactionRef){
        return ResponseEntity.ok(auditService.getAllAuditByTransactionRef(transactionRef));
    }
}

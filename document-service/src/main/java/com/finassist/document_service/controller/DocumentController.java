package com.finassist.document_service.controller;

import com.finassist.document_service.dto.DocumentResponse;
import com.finassist.document_service.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<DocumentResponse> uploadDocument(
            @RequestParam("file") MultipartFile file)
            throws IOException {

        DocumentResponse response =
                documentService.uploadDocument(file);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/documents")
    public ResponseEntity<List<DocumentResponse>> getAllDocuments(){
        return ResponseEntity.ok(documentService.getAllDocuments());
    }

    @GetMapping("/document/{documentId}")
    public ResponseEntity<DocumentResponse> getDocumentById(@PathVariable Long documentId){
        return ResponseEntity.ok(documentService.getDocumentById(documentId));
    }

    @DeleteMapping("/delete/document/{documentId}")
    public ResponseEntity<String> deleteDocumentById(@PathVariable Long documentId){
        return ResponseEntity.ok(documentService.deleteDocumentById(documentId));
    }
}

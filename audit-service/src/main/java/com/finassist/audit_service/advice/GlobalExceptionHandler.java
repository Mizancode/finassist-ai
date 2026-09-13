package com.finassist.audit_service.advice;

import com.finassist.audit_service.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleNotFound(ResourceNotFoundException exception){
            return buildResponse(exception.getMessage());
    }

    private ResponseEntity<Map<String, Object>> buildResponse(String message) {
        Map<String,Object> response=new HashMap<>();
        response.put("timeStamp", LocalDateTime.now());
        response.put("status", HttpStatus.NOT_FOUND);
        response.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        response.put("message",message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}

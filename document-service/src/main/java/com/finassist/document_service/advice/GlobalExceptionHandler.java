package com.finassist.document_service.advice;

import com.finassist.document_service.exception.BadResourceException;
import com.finassist.document_service.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadResourceException.class)
    public ResponseEntity<Map<String,Object>> handleBadRequest(BadResourceException exception){
        return buildResponse(HttpStatus.BAD_REQUEST,exception.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleResourceNotFound(ResourceNotFoundException exception){
        return buildResponse(HttpStatus.NOT_FOUND,exception.getMessage());
    }


    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus httpStatus, String message) {
        Map<String,Object> response=new HashMap<>();
        response.put("timeStamp", LocalDateTime.now());
        response.put("status", httpStatus);
        response.put("error", httpStatus.getReasonPhrase());
        response.put("message",message);
        return ResponseEntity.status(httpStatus).body(response);
    }


}

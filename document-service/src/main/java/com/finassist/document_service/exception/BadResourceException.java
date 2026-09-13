package com.finassist.document_service.exception;

public class BadResourceException extends RuntimeException {
    public BadResourceException(String message){
        super(message);
    }
}

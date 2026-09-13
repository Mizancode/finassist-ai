package com.finassist.ai_assistant_service.exception;

public class AiServiceException extends RuntimeException{

    public AiServiceException(String message){
        super(message);
    }

    public AiServiceException(String message, Throwable cause){
        super(message,cause);
    }
}

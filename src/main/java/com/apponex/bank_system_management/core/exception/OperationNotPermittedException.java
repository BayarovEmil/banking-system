package com.apponex.bank_system_management.core.exception;

public class OperationNotPermittedException extends RuntimeException{
    public OperationNotPermittedException() {
    }

    public OperationNotPermittedException(String message) {
        super(message);
    }
}

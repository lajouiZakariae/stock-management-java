package com.example.stock_management.exceptions;

public class InvalidFileOperationException extends RuntimeException {
    public InvalidFileOperationException(String message) {
        super(message);
    }
}

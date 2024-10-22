package com.mycompany.exception;

public class InvalidOperationException extends RuntimeException {
    public InvalidOperationException(String message) {
        super("Invalid operation: " + message);
    }
}

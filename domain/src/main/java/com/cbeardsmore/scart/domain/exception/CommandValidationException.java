package com.cbeardsmore.scart.domain.exception;

public class CommandValidationException extends RuntimeException {
    public CommandValidationException(String message) {
        super(message);
    }
}
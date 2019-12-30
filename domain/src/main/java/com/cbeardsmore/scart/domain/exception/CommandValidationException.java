package com.cbeardsmore.scart.domain.exception;

public class CommandValidationException extends RuntimeException {
    public CommandValidationException(String field) {
        super(String.format("%s cannot be null or blank.", field));
    }
}
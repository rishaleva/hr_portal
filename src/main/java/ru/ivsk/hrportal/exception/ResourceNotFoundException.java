package ru.ivsk.hrportal.exception;


public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String format, Object... args) {
        super(String.format(format, args));
    }
}

package ru.ivsk.hrportal.exception;

public class ManagerAlreadyExistsException extends RuntimeException {
    public ManagerAlreadyExistsException(String message) {
        super(message);
    }

    public ManagerAlreadyExistsException(String format, Object... args) {
        super(String.format(format, args));
    }
}

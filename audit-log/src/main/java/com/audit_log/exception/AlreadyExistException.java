package com.audit_log.exception;

public class AlreadyExistException extends RuntimeException {

    public AlreadyExistException(String properties) {
        super(properties + " já esta em uso!");
    }

    public AlreadyExistException(Long properties) {
        super("id: " + properties + ", já esta em uso!");
    }

    public AlreadyExistException(String properties, String message) {
        super(message);
    }
}

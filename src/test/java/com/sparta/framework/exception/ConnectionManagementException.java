package com.sparta.framework.exception;

public class ConnectionManagementException extends RuntimeException {

    private String message;

    public ConnectionManagementException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}

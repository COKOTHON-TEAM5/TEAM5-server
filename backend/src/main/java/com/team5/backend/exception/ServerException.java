package com.team5.backend.exception;

public class ServerException extends RuntimeException {
    public ServerException(String message) {
        super(message);
    }
}

package com.devsu.hackerearth.backend.client.infrastructure.shared.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
package com.example.demo.exception;

public class UserIsNotExistsException extends RuntimeException {
    public UserIsNotExistsException(String message) {
        super(message);
    }
}

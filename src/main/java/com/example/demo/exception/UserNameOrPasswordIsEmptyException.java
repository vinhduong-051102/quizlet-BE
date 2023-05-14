package com.example.demo.exception;

public class UserNameOrPasswordIsEmptyException extends RuntimeException {
    public UserNameOrPasswordIsEmptyException(String message) {
        super(message);
    }
}

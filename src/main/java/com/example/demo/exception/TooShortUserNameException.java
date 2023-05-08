package com.example.demo.exception;

public class TooShortUserNameException extends RuntimeException {
    public TooShortUserNameException(String message) {
        super(message);
    }
}

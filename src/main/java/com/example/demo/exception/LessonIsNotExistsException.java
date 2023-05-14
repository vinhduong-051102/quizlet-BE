package com.example.demo.exception;

public class LessonIsNotExistsException extends RuntimeException {
    public LessonIsNotExistsException(String message) {
        super(message);
    }
}

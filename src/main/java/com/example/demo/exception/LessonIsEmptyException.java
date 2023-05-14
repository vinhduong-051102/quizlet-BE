package com.example.demo.exception;

public class LessonIsEmptyException extends RuntimeException {
    public LessonIsEmptyException(String message) {
        super(message);
    }
}

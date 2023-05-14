package com.example.demo.exception;

public class TooLongVocabularyException extends RuntimeException {
    public TooLongVocabularyException(String message) {
        super(message);
    }
}

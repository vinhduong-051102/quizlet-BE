package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateUserNameException.class)
    public ResponseEntity<String> handleDuplicateUserNameException(DuplicateUserNameException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TooShortUserNameException.class)
    public ResponseEntity<String> handleTooShortUserNameException(DuplicateUserNameException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNameOrPasswordIsEmptyException.class)
    public ResponseEntity<String> handleUserNameOrPasswordIsEmptyException(DuplicateUserNameException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<String> handleInvalidUserException(DuplicateUserNameException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

//    @ExceptionHandler(LessonIsEmptyException.class)
//    public ResponseEntity<String> handleLessonIsEmptyException(DuplicateUserNameException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(UserIsNotExistsException.class)
    public ResponseEntity<String> handleUserIsNotExistsException(DuplicateUserNameException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidWordException.class)
    public ResponseEntity<String> handleInvalidWordException(DuplicateUserNameException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LessonIsNotExistsException.class)
    public ResponseEntity<String> handleLessonIsNotExistsException(DuplicateUserNameException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TooLongVocabularyException.class)
    public ResponseEntity<String> handleTooLongVocabularyException(DuplicateUserNameException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
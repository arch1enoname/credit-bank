package com.arthur.calculator.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class CalculatorExceptionHandler {

    @ExceptionHandler(value = {CalculatorException.class})
    public ResponseEntity<Object> handleProjectNotFoundException(CalculatorException calculatorException) {

        log.error("Credit exception occurred: ", calculatorException);

        Exception exception = new Exception(
                calculatorException.getMessage(),
                calculatorException.getCause(),
                HttpStatus.BAD_REQUEST
        );

        return new ResponseEntity<>(exception, exception.getHttpStatus());

    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(
            ValidationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getErrors().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}

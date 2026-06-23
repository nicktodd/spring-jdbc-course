package com.stocks.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TODO 1: Add @RestControllerAdvice annotation.
 *
 * TODO 2: Add a Logger field:
 *   private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
 *
 * TODO 3: Implement handleBadRequest(IllegalArgumentException ex):
 *   - @ExceptionHandler(IllegalArgumentException.class)
 *   - Log at WARN: log.warn("Business rule violation: {}", ex.getMessage())
 *   - Return ResponseEntity.badRequest().body(Map.of("error", "Bad Request", "message", ex.getMessage(), "timestamp", ...))
 *
 * TODO 4: Implement handleValidationErrors(MethodArgumentNotValidException ex):
 *   - @ExceptionHandler(MethodArgumentNotValidException.class)
 *   - Collect field errors using ex.getBindingResult().getFieldErrors()
 *   - Map each FieldError to: fieldName → defaultMessage
 *   - Return ResponseEntity.badRequest() with body {"error":"Validation Failed","fields":{...},"timestamp":...}
 *
 * Hint: Look at the demo GlobalExceptionHandler for the full implementation.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(IllegalArgumentException ex) {
        log.warn("Business rule violation: {}", ex.getMessage());
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error", "Bad Request");
        body.put("message", ex.getMessage());
        body.put("timestamp", Instant.now().toString());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> fields = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (a, b) -> a));
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error", "Validation Failed");
        body.put("fields", fields);
        body.put("timestamp", Instant.now().toString());
        return ResponseEntity.badRequest().body(body);
    }
}

package com.bank.controller;

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
 * GlobalExceptionHandler in Module 05 adds handling for validation errors.
 *
 * New in Module 05:
 *   MethodArgumentNotValidException is thrown when @Valid fails on a @RequestBody.
 *   The handler extracts the field-level errors and returns them as a structured map:
 *
 *   HTTP 400 Bad Request:
 *   {
 *     "error": "Validation Failed",
 *     "fields": {
 *       "accountNumber": "Account number must match ACC-NNN",
 *       "balance": "Opening balance cannot be negative"
 *     },
 *     "timestamp": "2024-06-01T09:15:00Z"
 *   }
 *
 * This is far more useful to API clients than a generic 400 message.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handles business-rule violations: duplicate account, insufficient funds.
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(IllegalArgumentException ex) {
        log.warn("Business rule violation: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(Map.of(
                "error", "Bad Request",
                "message", ex.getMessage(),
                "timestamp", Instant.now().toString()
        ));
    }

    // Handles @Valid failures on @RequestBody parameters.
    // MethodArgumentNotValidException contains the full list of FieldErrors.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        // Collect field name → default message from each FieldError.
        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        // If two constraints fail on the same field, keep both messages.
                        (msg1, msg2) -> msg1 + "; " + msg2
                ));

        log.warn("Validation failed: {}", fieldErrors);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error", "Validation Failed");
        body.put("fields", fieldErrors);
        body.put("timestamp", Instant.now().toString());

        return ResponseEntity.badRequest().body(body);
    }
}

package com.fdmgroup.plantpal_project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler that catches exceptions across the entire application
 * and returns consistent, user-friendly error responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final String ERROR_KEY = "error";
    private static final String STATUS_KEY = "status";
    private static final String VALIDATION_ERRORS_KEY = "validationErrors";

    /**
     * Handles cases when a requested resource is not found in the database
     * @return ResponseEntity with 404 NOT FOUND status and error details
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put(ERROR_KEY, ex.getMessage());
        errorResponse.put(STATUS_KEY, HttpStatus.NOT_FOUND.toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Handles validation errors when request body fails @Valid checks
     * @return ResponseEntity with 400 BAD REQUEST status and field-specific error details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Extract field-specific validation errors
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        // Build comprehensive error response with validation details
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put(ERROR_KEY, "Validation failed");
        errorResponse.put(STATUS_KEY, HttpStatus.BAD_REQUEST.toString());
        errorResponse.put(VALIDATION_ERRORS_KEY, errors);
        
        return ResponseEntity.badRequest().body(errorResponse);
    }
    
    /**
     * Handles already existing emails
     * @return ResponseEntity with 409 CONFLICT status and error details
     */
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put(ERROR_KEY, ex.getMessage());
        errorResponse.put(STATUS_KEY, HttpStatus.CONFLICT.toString());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    /**
     * Handles already existing usernames
     * @return ResponseEntity with 409 CONFLICT status and error details
     */
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUsernameAlreadyExists(UsernameAlreadyExistsException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put(ERROR_KEY, ex.getMessage());
        errorResponse.put(STATUS_KEY, HttpStatus.CONFLICT.toString());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    /**
     * Handles database constraint violations (like unique constraints)
     * @return ResponseEntity with 409 CONFLICT status and error details
     */
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolation(org.springframework.dao.DataIntegrityViolationException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        
        // Check if it's a unique constraint violation
        if (ex.getMessage() != null && ex.getMessage().contains("constraint") && ex.getMessage().contains("unique")) {
            errorResponse.put(ERROR_KEY, "Email or username already exists");
        } else {
            errorResponse.put(ERROR_KEY, "Database constraint violation");
        }
        errorResponse.put(STATUS_KEY, HttpStatus.CONFLICT.toString());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    /**
     * Handles all other unexpected exceptions that aren't caught by specific handlers
     * @return ResponseEntity with 500 INTERNAL SERVER ERROR status and generic error message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put(ERROR_KEY, "An internal server error occurred");
        errorResponse.put(STATUS_KEY, HttpStatus.INTERNAL_SERVER_ERROR.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
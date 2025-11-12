package com.fdmgroup.plantpal_project.exception;

/**
 * Custom exception thrown when a requested resource is not found in the database.
 * This is typically used for GET, PUT, DELETE operations with non-existent IDs.
 */
public class ResourceNotFoundException extends RuntimeException {
    
    /**
     * Creates a new ResourceNotFoundException with a custom message
     * @param message the detailed error message explaining what resource wasn't found
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
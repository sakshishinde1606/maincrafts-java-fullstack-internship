package com.maincrafts.contactapp.exception;

// Thrown when a contact with the requested ID doesn't exist.
// Caught by GlobalExceptionHandler and turned into a clean 404 JSON response.
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

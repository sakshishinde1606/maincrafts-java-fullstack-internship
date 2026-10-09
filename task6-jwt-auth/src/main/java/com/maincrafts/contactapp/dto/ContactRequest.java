package com.maincrafts.contactapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// Used for POST /contacts and PUT /contacts/{id}.
// These Bean Validation annotations are checked automatically because the
// controller methods use @Valid - invalid requests never reach the database.
public record ContactRequest(

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Message cannot be empty")
        String message,

        // optional - defaults to "PENDING" if left blank
        String status
) {
}

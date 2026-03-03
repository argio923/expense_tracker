package com.argio.exception.model;

/**
 * Thrown to indicate an error occurred during the creation of a user in the system.
 *
 * This exception serves as a specific subtype of {@code DomainException}, representing
 * domain-level errors related to user creation processes. It encapsulates details of
 * the failure, including a descriptive error message and an optional cause.
 *
 * Typical use cases include scenarios where user creation operations fail due to
 * violations of business rules, invalid input data, or other domain-specific issues.
 */
public class UserCreationException extends DomainException {

    public UserCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

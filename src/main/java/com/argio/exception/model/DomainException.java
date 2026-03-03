package com.argio.exception.model;

/**
 * Represents a base exception for domain-specific errors in the application.
 *
 * This class serves as a parent for exceptions that are specific to domain logic.
 * It extends {@code RuntimeException}, enabling unchecked exceptions to be thrown
 * for various business rule violations or domain-related conditions.
 *
 * Subclasses of {@code DomainException} typically encapsulate a specific type
 * of error, providing meaningful and descriptive error messages for clarity.
 *
 * This exception plays a key role in exception handling mechanisms where instances
 * of {@code DomainException} can be identified and processed to generate appropriate
 * responses, such as in RESTful API error handling scenarios.
 */
public class DomainException extends RuntimeException {
    protected DomainException(String message) {
        super(message);
    }

    protected DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}

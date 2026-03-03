package com.argio.exception.model;

/**
 * Thrown to indicate that a provided password is incorrect.
 *
 * This exception is a specific subtype of {@code DomainException}, representing
 * domain-level errors related to password validation and authentication processes.
 * It signals that a password provided during an operation, such as login, does not
 * match the expected credentials.
 *
 * Typical use cases include authentication systems where incorrect passwords must
 * be identified and handled, allowing appropriate error responses or retry mechanisms
 * to be implemented.
 */
public class WrongPasswordException extends DomainException {
    public WrongPasswordException(String message, Throwable cause) {
        super("Wrong password. Please try again.", cause);
    }

    public WrongPasswordException() {
        super("Wrong password. Please try again.");
    }
}

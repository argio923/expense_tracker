package com.argio.exception.model;

/**
 * Thrown to indicate that a security identity principal could not be found.
 *
 * This exception is a specific subtype of {@code DomainException}, representing
 * domain-level errors related to security identity management. It is used to signal
 * issues where a required principal, such as a user, role, or identity, is missing
 * or unavailable during the execution of security-related operations.
 *
 * Typical usage includes scenarios where applications need to enforce access control
 * or authenticate users, and a principal required for these processes cannot be located.
 */
public class SecurityIdentityPrincipalException extends DomainException {
    public SecurityIdentityPrincipalException() {
        super("Principal not found");
    }

    public SecurityIdentityPrincipalException(Throwable cause) {
        super("Principal not found", cause);
    }
}
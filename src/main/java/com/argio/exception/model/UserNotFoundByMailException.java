package com.argio.exception.model;

/**
 * Thrown to indicate that a user with a specified email address could not be found.
 *
 * This exception is a specific subtype of {@code DomainException}, representing
 * domain-level errors related to user lookup operations. It encapsulates a descriptive
 * error message that includes the email address used in the failed lookup attempt.
 *
 * Typical scenarios for throwing this exception include:
 * - When an authentication process is unable to locate a user record associated
 *   with the provided email.
 * - During user-related validations where an email address is required to
 *   correspond to an existing user in the system.
 */
public class UserNotFoundByMailException extends DomainException {
    public UserNotFoundByMailException(String email) {
        super("User with email '%s' not found".formatted(email));
    }
}

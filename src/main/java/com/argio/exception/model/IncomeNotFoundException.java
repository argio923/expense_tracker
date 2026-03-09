package com.argio.exception.model;

/**
 * Thrown to indicate that an income record could not be found.
 *
 * This exception is a specific subtype of {@code DomainException}, representing
 * domain-level errors related to income operations. It signals that a requested
 * income entry does not exist or cannot be retrieved.
 *
 * Common scenarios for throwing this exception include:
 * - When attempting to retrieve an income by its identifier, but no corresponding
 *   record is found in the data source.
 * - During business logic operations that depend on the existence of specific
 *   income records.
 */
public class IncomeNotFoundException extends DomainException {

    /**
     * Constructs a new {@code IncomeNotFoundException} with a default message
     * indicating that an income record could not be found.
     *
     * This exception is typically used in scenarios where an attempt to locate
     * an income entry fails, such as retrieving an income by its identifier
     * or during business logic operations dependent on the existence of income records.
     */
    public IncomeNotFoundException() {
        super("Income not found");
    }

    /**
     * Constructs a new {@code IncomeNotFoundException} with a default message
     * indicating that an income record could not be found and with a specified cause.
     *
     * This exception is typically used when an attempt to locate an income entry fails,
     * and there is an underlying exception that provides additional context about the
     * origin of the failure. The cause parameter is useful for error chaining and
     * diagnostic purposes.
     *
     * @param cause the underlying cause of this exception. It may be {@code null}.
     */
    public IncomeNotFoundException(Throwable cause) {
        super("Income not found", cause);
    }
}
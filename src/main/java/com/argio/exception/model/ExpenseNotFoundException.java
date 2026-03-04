package com.argio.exception.model;

/**
 * Thrown to indicate that an expense record could not be found.
 *
 * This exception is a specific subtype of {@code DomainException}, representing
 * domain-level errors related to expense operations. It signals that a requested
 * expense entry does not exist or cannot be retrieved.
 *
 * Common scenarios for throwing this exception include:
 * - When attempting to retrieve an expense by its identifier, but no corresponding
 *   record is found in the data source.
 * - During business logic operations that depend on the existence of specific
 *   expense records.
 */
public class ExpenseNotFoundException extends DomainException {

    /**
     * Constructs a new {@code ExpenseNotFoundException} with a default message
     * indicating that an expense record could not be found.
     *
     * This exception is typically used in scenarios where an attempt to locate
     * an expense entry fails, such as retrieving an expense by its identifier
     * or during business logic operations dependent on the existence of expense records.
     */
    public ExpenseNotFoundException() {
        super("Expense not found");
    }

    /**
     * Constructs a new {@code ExpenseNotFoundException} with a default message
     * indicating that an expense record could not be found, and a specified cause.
     *
     * This constructor is typically used when an underlying exception needs to be
     * associated with the failure to locate an expense. The cause parameter
     * provides additional context about the origin of the error.
     *
     * @param cause the underlying cause of this exception, allowing for error
     *              chaining and diagnostic purposes. It may be {@code null}.
     */
    public ExpenseNotFoundException(Throwable cause) {
        super("Expense not found", cause);
    }
}
package com.argio.assembler;

import com.argio.dto.expense.ExpenseResponseDto;
import com.argio.entity.Expense;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Objects;

/**
 * A utility class responsible for converting {@link Expense} domain entities into
 * {@link ExpenseResponseDto} data transfer objects for external use.
 *
 * This class provides conversion methods to map internal representations of expenses
 * into formats suitable for transmission to external clients, such as lightweight data
 * objects used in RESTful APIs or other external systems.
 *
 * Thread-Safety:
 * This class is annotated with {@code @ApplicationScoped}, ensuring that a single instance
 * is available per application lifecycle in a dependency injection context.
 *
 * Responsibilities:
 * - Converts {@link Expense} entities, which contain detailed information about an expense
 *   (e.g., ID, user association, amount, category, description, and date), into
 *   {@link ExpenseResponseDto} objects designed for external consumption.
 * - Ensures that non-null input is provided during conversions.
 */
@ApplicationScoped
public class ExpenseAssembler {

    /**
     * Converts an {@link Expense} entity into an {@link ExpenseResponseDto}.
     * This method is used to transform the domain entity into a lightweight data transfer object
     * suitable for external consumption, such as API responses.
     *
     * @param expense the {@link Expense} entity to be converted. Must not be null.
     * @return an {@link ExpenseResponseDto} containing details mapped from the provided {@link Expense} entity.
     * @throws NullPointerException if the provided {@code expense} is null.
     */
    public ExpenseResponseDto toResponseDto(Expense expense) {
        Objects.requireNonNull(expense);

        return new ExpenseResponseDto(
            expense.getId(),
            expense.getUserId(),
            expense.getAmount(),
            expense.getCategory(),
            expense.getDescription(),
            expense.getExpenseDate()
        );
    }

    /**
     * Converts a list of {@link Expense} entities into a list of {@link ExpenseResponseDto} objects.
     * This method is used to transform multiple domain entities into lightweight data transfer objects
     * suitable for external use, such as in API responses.
     *
     * @param expenseList the list of {@link Expense} entities to be converted. Must not be null.
     * @return a list of {@link ExpenseResponseDto} objects containing details mapped from the provided {@link Expense} entities.
     * @throws NullPointerException if the provided {@code expenseList} is null.
     */
    public List<ExpenseResponseDto> toResponseDto(List<Expense> expenseList) {
        return expenseList.stream().map(this::toResponseDto).toList();
    }
}
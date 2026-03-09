package com.argio.dto.expense;

import com.argio.enums.ExpenseCategory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents the data transfer object for returning information about an expense.
 * This record is used to encapsulate the details of an expense entry, including its
 * unique identifier, the associated user, and various attributes such as amount,
 * category, description, and date.
 *
 * Fields:
 * - {@code id}: The unique identifier of the expense.
 * - {@code userId}: The unique identifier of the user associated with the expense.
 * - {@code amount}: The monetary amount associated with the expense.
 * - {@code category}: The category to which the expense belongs, such as FOOD, TRANSPORT, etc.
 * - {@code description}: A textual description providing additional context for the expense.
 * - {@code expenseDate}: The date on which the expense was incurred.
 */
public record ExpenseResponseDto(
    UUID id,
    UUID userId,
    BigDecimal amount,
    ExpenseCategory category,
    String description,
    LocalDate expenseDate
) {}
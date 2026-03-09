package com.argio.dto.expense;

import com.argio.enums.ExpenseCategory;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents the data transfer object for registering a new expense.
 * This record encapsulates all necessary fields required to create
 * an expense entry in the system, including monetary value, category,
 * description, and the date of expense.
 *
 * Fields:
 * - {@code amount}: The monetary value of the expense. It must be a positive
 *   number with up to 10 digits in the integer part and up to 2 digits in the
 *   fractional part. This field cannot be null.
 * - {@code category}: The category of the expense, defined by the
 *   {@link ExpenseCategory} enum. This field cannot be null.
 * - {@code description}: A textual description providing additional information
 *   about the expense. The description must not be blank and is limited to
 *   a maximum of 500 characters.
 * - {@code expenseDate}: The date on which the expense was incurred. This date
 *   must not be in the future and cannot be null.
 */
public record RegisterExpenseDto(
    @NotNull
    @Positive
    @Digits(integer = 10, fraction = 2)
    BigDecimal amount,
    @NotNull
    ExpenseCategory category,
    @NotBlank
    @Size(max = 500)
    String description,
    @NotNull
    @PastOrPresent
    LocalDate expenseDate
) {}
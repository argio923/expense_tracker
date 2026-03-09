package com.argio.dto.expense;

import com.argio.enums.ExpenseCategory;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents the data transfer object for updating an existing expense entry.
 * This record encapsulates the fields required to modify an expense's details,
 * including its identifier, monetary value, category, description, and expense date.
 *
 * Fields:
 * - {@code id}: The unique identifier of the expense being updated. This field cannot be null.
 * - {@code amount}: The updated monetary amount of the expense. It must be a positive number with
 *   up to 10 digits in the integer part and up to 2 digits in the fractional part. This field cannot be null.
 * - {@code category}: The updated category of the expense, defined by the {@link ExpenseCategory} enum. This field cannot be null.
 * - {@code description}: The updated description providing additional details about the expense. It cannot be blank and must not exceed 500 characters.
 * - {@code expenseDate}: The updated date when the expense occurred. This date must be in the past or present and cannot be null.
 */
public record UpdateExpenseDto(
    @NotNull
    UUID id,
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
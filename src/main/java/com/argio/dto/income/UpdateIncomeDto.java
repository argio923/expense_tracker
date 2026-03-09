package com.argio.dto.income;

import com.argio.enums.IncomeCategory;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) for updating income details.
 *
 * This record is used to capture and validate the necessary fields
 * required for updating an existing income record in the system.
 * It includes validation constraints to ensure data integrity.
 *
 * Fields:
 * - id: A unique identifier for the income record being updated. Must not be null.
 * - amount: The monetary value of the income being updated. Must be a positive value
 *   with up to 10 integer digits and 2 fractional digits.
 * - category: The updated category of the income, represented by the {@link IncomeCategory} enum.
 * - description: A new or updated brief description of the income, limited to a maximum
 *   of 500 characters and must not be blank.
 * - incomeDate: The updated date of the income, which must not be in the future.
 */
public record UpdateIncomeDto(
    @NotNull
    UUID id,
    @NotNull
    @Positive
    @Digits(integer = 10, fraction = 2)
    BigDecimal amount,
    @NotNull
    IncomeCategory category,
    @NotBlank
    @Size(max = 500)
    String description,
    @NotNull
    @PastOrPresent
    LocalDate incomeDate
) {}
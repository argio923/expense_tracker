package com.argio.dto.income;

import com.argio.enums.IncomeCategory;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for registering income details.
 *
 * This record represents the necessary data for registering or recording
 * an income event within the system. It ensures that input data complies
 * with defined validation rules. This DTO is typically used for capturing
 * user input when creating a new income record.
 *
 * Fields:
 * - amount: The monetary value of the income, must be a positive value with up
 *   to 10 integer digits and 2 fractional digits.
 * - category: The category of the income, represented by the IncomeCategory
 *   enum, used for classification purposes.
 * - description: A brief description or note about the income, limited to a
 *   maximum of 500 characters.
 * - incomeDate: The date when the income was received, must not be in the
 *   future.
 */
public record RegisterIncomeDto(
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
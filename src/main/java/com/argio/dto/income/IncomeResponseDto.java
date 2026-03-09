package com.argio.dto.income;

import com.argio.enums.IncomeCategory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) representing a response for income details.
 *
 * This record serves as a container to encapsulate income-related data
 * fetched or returned by the service layer of the application. It is
 * typically used for transferring data between different layers of the
 * application or between the application and external systems.
 *
 * Fields:
 * - id: A unique identifier for the income record.
 * - userId: The unique identifier of the user associated with this income.
 * - amount: The monetary value of the income.
 * - category: The category of the income, represented by the {@link IncomeCategory} enum.
 * - description: A brief description or remark related to the income.
 * - incomeDate: The date on which the income was received.
 */
public record IncomeResponseDto(
        UUID id,
        UUID userId,
        BigDecimal amount,
        IncomeCategory category,
        String description,
        LocalDate incomeDate
) {}
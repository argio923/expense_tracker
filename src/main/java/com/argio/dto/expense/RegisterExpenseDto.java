package com.argio.dto.expense;

import com.argio.enums.ExpenseCategory;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RegisterExpenseDto(
    @NotBlank
    @Positive
    @Digits(integer = 10, fraction = 2)
    BigDecimal amount,
    @NotBlank
    ExpenseCategory category,
    @NotBlank
    @Size(max = 500)
    String description,
    @NotNull
    @PastOrPresent
    LocalDate expenseDate
) {}
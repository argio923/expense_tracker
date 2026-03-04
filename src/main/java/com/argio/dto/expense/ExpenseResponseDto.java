package com.argio.dto.expense;

import com.argio.enums.ExpenseCategory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ExpenseResponseDto(
    UUID id,
    UUID userId,
    BigDecimal amount,
    ExpenseCategory category,
    String description,
    LocalDate expenseDate


) {}
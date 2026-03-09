package com.argio.assembler;

import com.argio.dto.income.IncomeResponseDto;
import com.argio.entity.Income;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Objects;

/**
 * A utility class that provides methods for assembling {@link IncomeResponseDto} instances
 * from {@link Income} entities. This class is responsible for converting the internal representation
 * of an income entity into data transfer objects (DTOs) for external communication or API responses.
 *
 * This class is intended to be used within service or controller layers where business logic requires
 * transforming entity objects into lightweight DTOs to hide or abstract internal details of domain entities.
 *
 * Thread-Safety:
 * This class is annotated with {@code @ApplicationScoped}, ensuring a single instance is managed
 * within the application's dependency injection context during its lifecycle.
 *
 * Responsibilities:
 * - Convert an individual {@link Income} object into a {@link IncomeResponseDto}.
 * - Convert a collection of {@link Income} objects into a list of {@link IncomeResponseDto}.
 */
@ApplicationScoped
public class IncomeAssembler {

    /**
     * Converts an {@link Income} entity to an {@link IncomeResponseDto}.
     *
     * @param income the {@link Income} entity to be converted; must not be null
     * @return a corresponding {@link IncomeResponseDto} constructed from the provided {@link Income} entity
     * @throws NullPointerException if the input {@link Income} is null
     */
    public IncomeResponseDto toResponseDto(Income income) {
        Objects.requireNonNull(income);
        return new IncomeResponseDto(
            income.getId(),
            income.getUserId(),
            income.getAmount(),
            income.getCategory(),
            income.getDescription(),
            income.getIncomeDate()
        );
    }

    /**
     * Converts a list of {@link Income} entities into a list of {@link IncomeResponseDto} objects.
     *
     * Each {@link Income} entity in the input list is transformed into a corresponding
     * {@link IncomeResponseDto}, preserving the properties specified in the conversion logic.
     *
     * @param incomeList the list of {@link Income} entities to be converted; must not be null
     * @return a list of {@link IncomeResponseDto} objects mapped from the provided {@link Income} entities
     * @throws NullPointerException if the input {@code incomeList} is null
     */
    public List<IncomeResponseDto> toResponseDto(List<Income> incomeList) {
        return incomeList.stream().map(this::toResponseDto).toList();
    }
}
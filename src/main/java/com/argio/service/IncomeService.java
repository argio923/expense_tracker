package com.argio.service;

import com.argio.assembler.IncomeAssembler;
import com.argio.dto.income.IncomeResponseDto;
import com.argio.dto.income.RegisterIncomeDto;
import com.argio.dto.income.UpdateIncomeDto;
import com.argio.entity.Income;
import com.argio.enums.IncomeCategory;
import com.argio.exception.model.IncomeNotFoundException;
import com.argio.repository.IncomeRepository;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@ApplicationScoped
public class IncomeService {

    @Inject IncomeAssembler assembler;
    @Inject IncomeRepository repository;
    @Inject SecurityIdentity securityIdentity;
    @Inject UserService userService;

    /**
     * Retrieves an income record by its unique identifier and returns it as a {@link IncomeResponseDto}.
     *
     * This method fetches the income record with the specified ID by querying the underlying repository.
     * If no record with the given ID is found, an exception is thrown. The retrieved entity is then
     * converted into a {@link IncomeResponseDto} using the {@code assembler}.
     *
     * @param id the unique identifier of the income record to be retrieved; must*/
    public IncomeResponseDto get(UUID id) {
        var incomeEntity = repository.findById(id).orElseThrow();
        return assembler.toResponseDto(incomeEntity);
    }

    /**
     * Retrieves a list of income records for the currently authenticated user within a specified date range
     * and optionally filtered by income category.
     *
     * If the {@code dateFrom} or {@code dateTo} parameters are null, default values will be used. The default
     * value for {@code dateFrom} is the first day of the current month, and the default value for {@code dateTo}
     * is the current date. The income records are fetched from the repository and converted to a list of
     * {@link IncomeResponseDto} objects.
     *
     * @param dateFrom the start date of the range within which the income records must fall;
     *                 if null, defaults to the first day of the current month
     * @param dateTo the end date of the range within which the income records must fall;
     *               if null, defaults to the current date
     * @param category the category of income to filter by; if null, all income categories are included
     * @return a list of {@link IncomeResponseDto} objects representing the income records
     */
    public List<IncomeResponseDto> getListByUser(
            LocalDate dateFrom,
            LocalDate dateTo,
            IncomeCategory category
    ) {
        var email = securityIdentity.getPrincipal().getName();
        var today = LocalDate.now();

        if(dateFrom == null)
            dateFrom = today.withDayOfMonth(1);

        if(dateTo == null)
            dateTo = today;

        var incomeList = repository.findList(email, category, dateFrom, dateTo);
        return assembler.toResponseDto(incomeList);
    }

    /**
     * Updates an existing income record with new details provided in the {@code toUpdate} DTO.
     *
     * This method retrieves the income record corresponding to the unique identifier
     * from the {@code toUpdate} object. If the record exists, it updates the record
     * using the provided values for amount, category, description, and income date.
     * If no record is found, an {@code IncomeNotFoundException} is thrown.
     *
     * @param toUpdate the {@link UpdateIncomeDto} object containing the updated details
     *                 for an existing income record. It must include a valid unique
     *                 identifier and valid field data for updating.
     */
    @Transactional
    public void updateIncome(UpdateIncomeDto toUpdate) {
        var incomeEntity = repository.findById(toUpdate.id()).orElseThrow(() -> new IncomeNotFoundException());

        incomeEntity.updateValues(
            toUpdate.amount(),
            toUpdate.category(),
            toUpdate.description(),
            toUpdate.incomeDate()
        );

        repository.persist(incomeEntity);
    }

    /**
     * Registers a new income record in the system.
     *
     * This method creates and persists an {@link Income} entity using the details
     * provided in the {@link RegisterIncomeDto}. The currently authenticated user's
     * information is fetched and associated with the new income record.
     * The method ensures that the provided DTO is not null before proceeding.
     *
     * @param dto the {@link RegisterIncomeDto} containing the details of the income
     *            to be registered. It must include a valid amount, category,
     *            description, and income date. The income date cannot be in the future.
     * @throws NullPointerException if the {@code dto} parameter is null.
     */
    @Transactional
    public void registerIncome(RegisterIncomeDto dto) {
        Objects.requireNonNull(dto);
        var userEntity = userService.getEntityByEmail();

        var incomeEntity = new Income(
                userEntity.getId(),
                userEntity,
                dto.amount(),
                dto.category(),
                dto.description(),
                dto.incomeDate()
        );

        repository.persist(incomeEntity);
    }

    /**
     * Deletes an income record identified by its unique identifier.
     * This method removes the income from the data source by leveraging
     * the repository layer.
     *
     * @param incomeId the unique identifier of the income to be deleted;
     *                 must not be null.
     */
    @Transactional
    public void deleteIncome(UUID incomeId) {
        repository.deleteExpense(incomeId);
    }
}
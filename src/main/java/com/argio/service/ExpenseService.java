package com.argio.service;

import com.argio.assembler.ExpenseAssembler;
import com.argio.dto.expense.ExpenseResponseDto;
import com.argio.dto.expense.RegisterExpenseDto;
import com.argio.dto.expense.UpdateExpenseDto;
import com.argio.entity.Expense;
import com.argio.enums.ExpenseCategory;
import com.argio.exception.model.ExpenseNotFoundException;
import com.argio.repository.ExpenseRepository;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@ApplicationScoped
@Authenticated
public class ExpenseService {

    @Inject ExpenseAssembler assembler;
    @Inject ExpenseRepository repository;
    @Inject SecurityIdentity securityIdentity;
    @Inject UserService userService;

    /**
     * Retrieves an expense entry by its unique identifier and converts it into a response DTO.
     * This method fetches the expense from the database, and if the expense exists,
     * it transforms the entity into a lightweight {@code ExpenseResponseDto}.
     * If the expense is not found, an {@code ExpenseNotFoundException} is thrown.
     *
     * @param id the unique identifier of the expense to be retrieved; must not be null.
     * @return an {@code ExpenseResponseDto} containing the details of the retrieved expense.
     * @throws ExpenseNotFoundException if no expense is found with the given identifier.
     */
    public ExpenseResponseDto get(UUID id) {
        var expenseEntity = repository.findById(id).orElseThrow(()-> new ExpenseNotFoundException());
        return assembler.toResponseDto(expenseEntity);
    }

    /**
     * Retrieves a list of expenses for the currently authenticated user, filtered by the provided date range and category.
     * This method fetches expenses based on the user's email, converts them to response DTOs, and returns the result.
     *
     * @param dateFrom the starting date of the expense filter range (inclusive); can be null to ignore this filter.
     * @param dateTo the ending date of the expense filter range (inclusive); can be null to ignore this filter.
     * @param category the category to filter the expenses by; can be null to include all categories.
     * @return a list of {@link ExpenseResponseDto} objects representing the user's expenses that match the given criteria.
     */
    public List<ExpenseResponseDto> getListByUser(
        LocalDate dateFrom,
        LocalDate dateTo,
        ExpenseCategory category
    ) {
        var email = securityIdentity.getPrincipal().getName();
        var today = LocalDate.now();

        if(dateFrom == null)
            dateFrom = today.withDayOfMonth(1);

        if(dateTo == null)
            dateTo = today;

        var expenseList = repository.findList(email, category, dateFrom, dateTo);
        return assembler.toResponseDto(expenseList);
    }

    /**
     * Updates the details of an existing expense entry based on the provided data.
     * This method retrieves the expense entity by its unique identifier, applies
     * the updated values, and ensures the changes are persisted.
     *
     * @param toUpdate the data transfer object containing the updated details
     *                 of the expense, including identifier, amount, category,
     *                 description, and expense date; must not be null.
     * @throws ExpenseNotFoundException if no expense is found with the given identifier.
     */
    @Transactional
    public void updateExpense(UpdateExpenseDto toUpdate) {
        var expenseEntity = repository.findById(toUpdate.id()).orElseThrow(() -> new ExpenseNotFoundException());

        expenseEntity.updateValues(
            toUpdate.amount(),
            toUpdate.category(),
            toUpdate.description(),
            toUpdate.expenseDate()
        );

        repository.persist(expenseEntity);
    }

    /**
     * Registers a single expense in the system for the currently authenticated user.
     * This method creates a new {@link Expense} entity based on the provided data and persists it to the database.
     *
     * @param dto the data transfer object containing the details of the expense to be registered, including amount, category, description, and expense date;
     *            must not be null.
     * @throws NullPointerException if the provided {@code dto} is null.
     */
    @Transactional
    public void registerExpense(RegisterExpenseDto dto) {
        Objects.requireNonNull(dto);
        var userEntity = userService.getEntityByEmail();

        var expense = new Expense(
                userEntity.getId(),
                userEntity,
                dto.amount(),
                dto.category(),
                dto.description(),
                dto.expenseDate()
        );

        repository.persist(expense);
    }

    /**
     * Registers a list of expenses for the currently authenticated user.
     * Each expense in the provided list is individually processed and persisted to the database.
     *
     * @param expenseList a list of {@link RegisterExpenseDto} objects containing the details
     *                    of each expense to be registered, including amount, category,
     *                    description, and expense date; must not be null.
     * @throws NullPointerException if the provided {@code expenseList} is null.
     */
    @Transactional
    public void registerExpenseList(List<RegisterExpenseDto> expenseList) {
        Objects.requireNonNull(expenseList);
        expenseList.forEach(this::registerExpense);
    }

    /**
     * Deletes an expense identified by the provided unique expense ID.
     * This method removes the corresponding {@code Expense} entity from the database.
     *
     * @param expenseId the unique identifier of the expense to be deleted; must not be null.
     *                  Passing a null value will result in a {@code NullPointerException}.
     */
    @Transactional
    public void deleteExpense(UUID expenseId) {
        repository.deleteExpense(expenseId);
    }
}
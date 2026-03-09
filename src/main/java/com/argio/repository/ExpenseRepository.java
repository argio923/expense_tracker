package com.argio.repository;

import com.argio.entity.Expense;
import com.argio.enums.ExpenseCategory;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for performing CRUD operations on {@link Expense} entities.
 * This class leverages PanacheRepository to simplify database interactions.
 * It provides a method to retrieve expenses associated with a specific user.
 *
 * The repository is scoped to the application lifecycle to ensure a shared instance
 * is used throughout the application.
 */
@ApplicationScoped
public class ExpenseRepository implements PanacheRepository<Expense> {

    /**
     * Retrieves a list of {@code Expense} entities for a specific user within a given date range
     * and optionally filtered by expense category. The query also ensures that only non-deleted
     * expenses are returned.
     *
     * @param userMail the email of the user whose expenses are to be retrieved; must not be null.
     * @param category an optional category filter for the expenses; can be null to retrieve expenses
     *                 of all categories.
     * @param dateBy the start date of the range within which expenses should be retrieved; must not be null.
     * @param dateTo the end date of the range within which expenses should be retrieved; must not be null.
     * @return a list of {@code Expense} entities satisfying the query parameters; the list will
     *         be empty if no expenses match the criteria.
     */
    public List<Expense> findList(
        String userMail,
        ExpenseCategory category,
        LocalDate dateBy,
        LocalDate dateTo
    ) {
        var query = new StringBuilder();

        query.append("user.email = :mail and deleted = false");
        var params = Parameters.with("mail", userMail);

        query.append(" and expenseDate >= :dateBy");
        params.and("dateBy", dateBy);

        query.append(" and expenseDate <= :dateTo");
        params.and("dateTo", dateTo);

        if(category != null) {
            query.append(" and category = :category");
            params.and("category", category);
        }

        return list(query.toString(), params);
    }

    /**
     * Retrieves an {@code Expense} entity by its unique identifier.
     * This method searches for a specific expense in the database based on
     * the given expense ID and returns it wrapped in an {@code Optional}.
     *
     * @param expenseId the unique identifier of the expense to be retrieved; must not be null.
     * @return an {@code Optional} containing the {@code Expense} entity if found,
     * or an empty {@code Optional} if no expense exists with the given ID.
     */
    public Optional<Expense> findById(UUID expenseId) {
        return findById(expenseId);
    }

    /**
     * Deletes an {@code Expense} entity identified by the given unique expense ID.
     * This method removes the expense from the database.
     *
     * @param expenseId the unique identifier of the expense to be deleted; must not be null.
     */
    public void deleteExpense(UUID expenseId) {
        deleteExpense(expenseId);
    }
}
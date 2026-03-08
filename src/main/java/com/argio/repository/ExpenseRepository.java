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
     * Retrieves a list of {@code Expense} entities that match the given criteria.
     * This method filters expenses based on the user's email, expense category,
     * and an optional date range, while excluding deleted expenses.
     *
     * @param userMail the email of the user to whom the expenses belong; must not be null.
     * @param category the category to filter the expenses by; can be null to include all categories.
     * @param dateBy the start date to filter expenses by (inclusive); can be null to ignore this filter.
     * @param dateTo the end date to filter expenses by (inclusive); can be null to ignore this filter.
     * @return a list of {@code Expense} entities that match the provided filter criteria, or an empty list if no matching expenses are found.
     */
    public List<Expense> findList(
        String userMail,
        ExpenseCategory category,
        LocalDate dateBy,
        LocalDate dateTo
    ) {
        var query = new StringBuilder(
            "user.email = :mail and deleted = false"
        );
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
     * Persists a new {@code Expense} entity to the database.
     * This method saves a new expense to the database, associating it with the user
     * identified by the given user ID.
     *
     * @param expense the {@code Expense} entity to be persisted; must not be null.
     */
    public void persistExpense(Expense expense) {
        persist(expense);
    }

    /**
     * Persists a list of {@code Expense} entities to the database.
     * This method saves a list of expenses to the database, associating each expense with the user
     * identified by the given user ID.
     *
     * @param expenseList the list of {@code Expense} entities to be persisted; must not be null.
     */
    public void persistExpenseList(List<Expense> expenseList) {
        persist(expenseList);
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
package com.argio.repository;

import com.argio.entity.Expense;
import com.argio.enums.ExpenseCategory;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

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
     * Retrieves a list of {@code Expense} entities associated with a specific user.
     * This method queries the database for expenses that belong to the user identified
     * by the given user ID.
     *
     * @param userId the unique identifier of the user whose expenses are to be retrieved; must not be null.
     * @return a {@code List} containing {@code Expense} entities associated with the given user ID,
     * or an empty list if no expenses are found for the user.
     */
    public List<Expense> findListByUser(UUID userId) {
        return list("userId", userId);
    }

    /**
     * Retrieves a list of {@code Expense} entities associated with a specific user and category.
     * This method queries the database for expenses that belong to the user identified by the given
     * user ID and match the specified expense category.
     *
     * @param category the specific {@code ExpenseCategory} to filter expenses by; must not be null.
     * @param userId   the unique identifier of the user whose expenses are to be retrieved; must not be null.
     * @return a {@code List} containing {@code Expense} entities that belong to the specified user
     * and match the given category, or an empty list if no matching expenses are found.
     */
    public List<Expense> findListByCategoryAndUser(ExpenseCategory category, UUID userId) {
        return list("userId = ?1 and category = ?2", userId, category);
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
}
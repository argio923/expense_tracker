package com.argio.repository;

import com.argio.entity.Income;
import com.argio.enums.IncomeCategory;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for managing {@link Income} entities.
 * This class provides database operations through the PanacheRepository interface,
 * allowing for simplified data access and query execution for income data.
 *
 * The repository is scoped to the application lifecycle, ensuring a shared instance is used
 * to handle income-related queries and interactions within the application.
 */
@ApplicationScoped
public class IncomeRepository implements PanacheRepository<Income> {

    /**
     * Retrieves a list of {@link Income} records based on the specified criteria.
     *
     * The method filters income records by the user's email, the income category (if provided),
     * and whether the income date falls within the specified date range.
     * Only non-deleted income records are included in the result.
     *
     * @param userMail the email address of the user whose income records are being queried
     * @param category the category of income to filter by, or null to include all categories
     * @param dateBy the start date of the range within which the income records must fall
     * @param dateTo the end date of the range within which the income records must fall
     * @return a list of {@link Income} entities matching the specified criteria
     */
    public List<Income> findList(
            String userMail,
            IncomeCategory category,
            LocalDate dateBy,
            LocalDate dateTo
    ) {
        var query = new StringBuilder();

        query.append("user.email = :mail and deleted = false");
        var params = Parameters.with("mail", userMail);

        query.append(" and incomeDate >= :dateBy");
        params.and("dateBy", dateBy);

        query.append(" and incomeDate <= :dateTo");
        params.and("dateTo", dateTo);

        if(category != null) {
            query.append(" and category = :category");
            params.and("category", category);
        }

        return list(query.toString(), params);
    }

    /**
     * Retrieves an {@link Income} entity based on its unique identifier.
     *
     * This method queries the database for an income record with the specified
     * unique identifier. If a matching record exists, it is returned wrapped
     * in an {@link Optional}; otherwise, an empty {@link Optional} is returned.
     *
     * @param id the unique identifier of the income record to retrieve
     * @return an {@link Optional} containing the matching {@link Income} entity,
     *         or an empty {@link Optional} if no matching record is found
     */
    public Optional<Income> findById(UUID id) {
        return findById(id);
    }

    /**
     * Deletes an expense record identified by its unique identifier.
     * This method ensures the removal of the expense from the data source.
     *
     * @param expenseId the unique identifier of the expense to be deleted; must not be null.
     */
    public void deleteExpense(UUID expenseId) {
        deleteExpense(expenseId);
    }
}
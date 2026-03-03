package com.argio.entity;

import com.argio.enums.ExpenseCategory;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents an expense entity in the system, used to record financial expenditures
 * made by users. This class maps to the "expenses" table in the database and includes
 * attributes related to the expense details such as the amount, category, description,
 * and timestamps.
 *
 * Features:
 * - Each expense is uniquely identified by a UUID.
 * - Includes user-specific association through the userId field.
 * - Categorization of expenses using predefined categories from the {@code ExpenseCategory} enum.
 * - Optional description for additional expense details.
 * - Timestamps to track when the expense was recorded and created.
 * - Soft delete functionality supported through the deleted flag.
 *
 * Persistence:
 * - Managed by JPA as a persistent entity with annotations for database mapping.
 * - Extends PanacheEntityBase to utilize utility methods for database operations.
 *
 * Lifecycle Management:
 * - Automatically sets a creation timestamp before persisting new entities.
 *
 * Constraints:
 * - The "id", "userId", "amount", "category", "expenseDate", and "createdAt" fields are mandatory.
 * - The "description" field has a maximum length of 500 characters.
 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // richiesto da JPA
@Entity
@Table(name = "expenses")
public class Expense extends PanacheEntityBase {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "category", nullable = false, columnDefinition = "expense_category")
    private ExpenseCategory category;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "expense_date", nullable = false)
    private LocalDate expenseDate;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    /**
     * Lifecycle callback method invoked before the entity is persisted to the database.
     * This method automatically sets the {@code createdAt} field to the current timestamp
     * if it has not been initialized. Ensures that the creation time is recorded for every
     * new {@code Expense} entity.
     *
     * Behavior:
     * - If the {@code createdAt} field is null, it is updated with the current system time
     *   in UTC using {@code Instant.now()}.
     *
     * Purpose:
     * - Enables automatic population of the {@code createdAt} field during the persistence phase,
     *   ensuring consistency and avoiding manual intervention in setting the timestamp.
     *
     * Constraints:
     * - This method only executes when JPA determines that the entity is being persisted
     *   for the first time.
     *
     * Note:
     * - The {@code @PrePersist} annotation ensures this method is invoked during the entity's
     *   lifecycle before it is written to the database.
     */
    @PrePersist
    void prePersist() {
        if (createdAt == null) createdAt = Instant.now();
    }
}
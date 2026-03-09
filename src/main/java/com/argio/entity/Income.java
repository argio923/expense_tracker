package com.argio.entity;

import com.argio.enums.IncomeCategory;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents an income record associated with a specific user.
 *
 * This entity maps to the "incomes" table in the database and defines
 * fields for storing information about the income, such as the amount,
 * category, description, and the associated user. The entity uses JPA
 * annotations to define the table schema and relationships.
 *
 * Key properties include:
 * - `userId`: The unique identifier of the associated user.
 * - `user`: The user entity this income belongs to.
 * - `amount`: The monetary value of the income.
 * - `category`: The income category, using an enum to define valid values.
 * - `description`: An optional description or note about the income.
 * - `incomeDate`: The date the income was received or registered.
 * - `createdAt`: The timestamp indicating when this record was created.
 * - `deleted`: A flag to mark whether the income record is deleted.
 *
 * The class also includes a lifecycle callback (`prePersist`) to set
 * default values for certain fields before the entity is persisted.
 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "incomes")
public class Income extends PanacheEntityBase {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private User user;

    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "category", nullable = false, columnDefinition = "income_category")
    private IncomeCategory category;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "income_date", nullable = false)
    private LocalDate incomeDate;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    /**
     * Constructs a new Income instance with the given parameters.
     *
     * @param userId      the unique identifier of the user associated with this income
     * @param user        the user entity associated with this income
     * @param amount      the amount of income, represented as a BigDecimal
     * @param category    the category of the income, specified as an IncomeCategory enum
     * @param description a textual description or note about the income
     * @param incomeDate  the date the income was received or recorded
     */
    public Income(
            UUID userId,
            User user,
            BigDecimal amount,
            IncomeCategory category,
            String description,
            LocalDate incomeDate
    ) {
        this.userId = userId;
        this.user = user;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.incomeDate = incomeDate;
    }

    /**
     * Callback method that is automatically invoked by the persistence context
     * before an entity is persisted. Sets the `createdAt` field to the current
     * timestamp and initializes the `deleted` field to `false`.
     */
    @PrePersist
    void prePersist() {
        createdAt = Instant.now();
        deleted = false;
    }

    /**
     * Updates the values of the income fields with the provided parameters.
     *
     * @param amount      the new monetary amount of the income, represented as a BigDecimal
     * @param category    the new category of the income, specified as an IncomeCategory enum
     * @param description a new textual description or note about the income
     * @param incomeDate  the new date when the income was received or recorded
     */
    public void updateValues(
        BigDecimal amount,
        IncomeCategory category,
        String description,
        LocalDate incomeDate
    ) {
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.incomeDate = incomeDate;
    }
}
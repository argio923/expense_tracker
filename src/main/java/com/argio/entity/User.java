package com.argio.entity;

import com.argio.enums.UserRole;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a user entity in the system, primarily used for authentication and authorization purposes.
 * This class maps to the "users" table in the database and contains user-specific attributes such as email,
 * hashed password, role, and account creation timestamp.
 *
 * Features:
 * - Unique and immutable email as the identifier for the user.
 * - Securely hashed password for authentication.
 * - Role-based access management using predefined roles like USER and ADMIN.
 * - Timestamp for when the user account was created.
 *
 * Persistence:
 * - This entity is managed by JPA with annotations for database mapping.
 * - PanacheEntityBase is extended to provide utility methods for database operations.
 *
 * Note:
 * - Default role is USER.
 * - The no-argument constructor is protected, as it is primarily used by JPA.
 * - The class includes a method for securely updating the hashed password.
 */
@Getter
@AllArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User extends PanacheEntityBase {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false, updatable = false, length = 255)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(nullable = false, updatable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    /**
     * Default no-argument constructor for the {@code User} entity.
     * This constructor is protected as it is intended to be used primarily by JPA.
     * It ensures the proper initialization of the {@code User} class when
     * creating instances from database queries.
     */
    protected User() {}

    /**
     * Constructs a new {@code User} instance with the specified email and password hash.
     * This constructor is used to create users with a unique email and a securely hashed password.
     *
     * @param email the unique email address of the user; must be non-null, valid, and immutable.
     * @param passwordHash the securely hashed password of the user; must be non-null and generated using a secure algorithm.
     */
    public User(
        String email,
        String passwordHash
    ) {
        this.email = email;
        this.passwordHash = passwordHash;
    }

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

    /**
     * Updates the password hash of the user with the specified new hash value.
     * This method ensures that the new password hash is not null.
     *
     * @param newPasswordHash the new securely hashed password for the user; must be non-null.
     *                        Passing a null value will result in a {@code NullPointerException}.
     */
    public void changePassword(String newPasswordHash) {
        this.passwordHash = Objects.requireNonNull(newPasswordHash);
    }
}
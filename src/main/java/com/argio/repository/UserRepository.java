package com.argio.repository;

import com.argio.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for performing CRUD operations on {@link User} entities.
 * This class uses PanacheRepository to simplify interaction with the database.
 * It provides specific methods for user-related queries and actions, such as finding users
 * by email, checking the existence of a user by email, and persisting user entities.
 *
 * The repository is scoped to the application lifecycle to ensure a shared instance is used.
 */
@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    /**
     * Finds a user by their email address.
     * This method queries the database for a {@link User} entity that matches the provided email.
     * If a user with the given email exists, it is returned as an {@link Optional}. If no user is found,
     * an empty {@link Optional} is returned.
     *
     * @param email the email address of the user to find; must not be null or empty.
     * @return an {@link Optional} containing the {@link User} if found, or an empty {@link Optional} if no user exists with the given email.
     */
    public Optional<User> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }

    /**
     * Checks if a user with the given email address exists in the database.
     *
     * @param email the email address to check for existence; must not be null or empty.
     * @return {@code true} if a user with the given email exists, {@code false} otherwise.
     */
    public boolean existsByMail(String email) {
        return count("email", email) > 0;
    }

    /**
     * Persists a {@link User} entity in the database.
     * This method is responsible for saving the provided user instance to ensure it is stored persistently.
     *
     * @param user the {@link User} entity to persist; must not be null and should have valid properties.
     */
    public void persistUser(User user) {
        persist(user);
    }

    /**
     * Finds an active {@link User} by their unique identifier.
     * This method queries the database using the given ID and returns an {@link Optional} containing the user if found,
     * or an empty {@link Optional} if no active user with the specified ID exists.
     *
     * @param id the unique identifier of the user to find; must not be null.
     * @return an {@link Optional} containing the {@link User} if an active user is found, or an empty {@link Optional} if no matching user exists.
     */
    public Optional<User> findActiveById(UUID id) {
        return find("id = ?1", id).firstResultOptional();
    }
}
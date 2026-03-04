package com.argio.assembler;

import com.argio.dto.user.UserResponseDto;
import com.argio.entity.User;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Objects;

/**
 * A utility class responsible for converting {@link User} domain entities into {@link UserResponseDto}
 * data transfer objects for external use. This class provides methods to map the internal representation
 * of a user entity into a format suitable for transmission to external clients.
 *
 * This is typically used in service or controller layers to serialize User objects into lightweight
 * representations for RESTful responses or other forms of data exchange.
 *
 * Thread-Safety:
 * This class is annotated with {@code @ApplicationScoped}, ensuring that a single instance is available
 * per application lifecycle in a dependency injection context.
 *
 * Responsibilities:
 * - Converts {@code User} entities, which may include complex internal logic and additional fields, into
 *   {@code UserResponseDto} objects containing only the necessary data for external consumers.
 */
@ApplicationScoped
public class UserAssembler {

    /**
     * Converts a {@link User} entity into a {@link UserResponseDto} data transfer object.
     * This method extracts the essential details from the {@code User} entity including
     * the user's unique ID, email, role, and account creation timestamp, and maps them to
     * a DTO for external representation.
     *
     * @param user the {@link User} entity to convert; can be {@code null}. If {@code null}, the method
     *             returns {@code null}.
     * @return a {@link UserResponseDto} containing the user's ID, email, role, and creation timestamp,
     *         or {@code null} if the input {@code user} is {@code null}.
     */
    public UserResponseDto toResponseDto(User user) {
        Objects.requireNonNull(user);

        return new UserResponseDto(
            user.getId(),
            user.getEmail(),
            user.getRole().name(),
            user.getCreatedAt()
        );
    }
}
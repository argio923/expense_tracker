package com.argio.dto.user;

import java.time.Instant;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) representing a user's response data.
 *
 * This record encapsulates key details about a user that are typically required
 * when returning user-related information from the API. The fields include:
 * - `id`: A unique identifier for the user.
 * - `email`: The email address associated with the user account.
 * - `role`: The role assigned to the user, which can reflect their permissions or access level.
 * - `createdAt`: The timestamp indicating when the user account was created.
 *
 * Instances of this DTO are typically returned by services or controllers when
 * providing user-related information, such as during account registration or when
 * viewing user profiles.
 */
public record UserResponseDto(
    UUID id,
    String email,
    String role,
    Instant createdAt
) {}
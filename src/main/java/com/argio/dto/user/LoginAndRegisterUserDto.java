package com.argio.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) used for user login and registration.
 *
 * This record encapsulates the essential information required for a user
 * to log in or register to the system, namely the user's email and password.
 * Both fields are validated to ensure correctness and security.
 *
 * Validation Constraints:
 * - `email`: Must be non-blank and adhere to a valid email format.
 * - `password`: Must be non-blank, 8-16 characters in length, and include at least
 *   one lowercase letter, one uppercase letter, one number, and one special character.
 */
public record LoginAndRegisterUserDto(
    @NotBlank
    @Email
    String email,

    @NotBlank
    @Size(
        min = 8,
        max = 16,
        message = "Password must be 8-16 chars and include at least 1 lowercase, 1 uppercase, 1 number, and 1 special character"
    )
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,16}$",
        message = "Password must be 8-16 chars and include at least 1 lowercase, 1 uppercase, 1 number, and 1 special character"
    )
    String password
) {}
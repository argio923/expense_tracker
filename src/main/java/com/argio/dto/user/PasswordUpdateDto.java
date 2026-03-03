package com.argio.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) used for updating a user's password.
 *
 * This record encapsulates the information required to change the password of an authenticated user,
 * including the current password and the new password to be set. Both fields are validated to ensure correctness and security.
 *
 * Constraints:
 * - `oldPassword`: Must not be blank.
 * - `newPassword`: Must not be blank and must adhere to the following rules:
 *   - Be between 8 and 16 characters in length.
 *   - Contain at least one lowercase letter.
 *   - Contain at least one uppercase letter.
 *   - Contain at least one numeric digit.
 *   - Contain at least one special character.
 *
 * Validation annotations ensure the provided input adheres to these constraints.
 * Instances of this DTO are typically consumed by methods handling password update operations.
 */
public record PasswordUpdateDto(
        @NotBlank
        String oldPassword,

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
        String newPassword
) {}
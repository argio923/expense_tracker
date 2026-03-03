package com.argio.dto.user;

import jakarta.validation.constraints.NotBlank;

public record PasswordUpdateDto(
        @NotBlank
        String email,
        @NotBlank
        String oldPassword,
        @NotBlank
        String newPassword
) {}
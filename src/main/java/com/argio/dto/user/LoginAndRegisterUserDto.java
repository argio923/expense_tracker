package com.argio.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginAndRegisterUserDto(
    @NotBlank
    @Email
    String email,
    @NotBlank
    String password
) {}
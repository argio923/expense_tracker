package com.argio.dto.user;

import java.time.Instant;
import java.util.UUID;

public record UserResponseDto(
    UUID id,
    String email,
    String role,
    Instant createdAt
) {}
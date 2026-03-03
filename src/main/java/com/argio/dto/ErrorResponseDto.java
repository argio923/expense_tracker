package com.argio.dto;

public record ErrorResponseDto(
    String message,
    int status
) {}
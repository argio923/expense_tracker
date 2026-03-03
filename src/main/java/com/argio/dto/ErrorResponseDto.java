package com.argio.dto;

/**
 * A data transfer object representing an error response.
 *
 * This record encapsulates the details of an error response that can be sent
 * to the client, typically in the context of exception handling within a
 * global exception mapper. It contains an error message providing details
 * about the error and an HTTP status code to indicate the nature of the error.
 *
 * Common uses of this DTO might include returning error information for
 * domain-specific exceptions, such as validation failures or system errors.
 */
public record ErrorResponseDto(
    String message,
    int status
) {}
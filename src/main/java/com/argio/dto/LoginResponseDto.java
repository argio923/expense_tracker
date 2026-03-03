package com.argio.dto;

/**
 * A data transfer object representing the response of a successful login operation.
 *
 * This record encapsulates an access token issued to the user upon authentication.
 * The access token is typically used for subsequent requests to authorize actions
 * on behalf of the authenticated user.
 *
 * Commonly used in the context of authentication services such as {@code AuthService}
 * to provide an authorization token after validating the user's credentials.
 */
public record LoginResponseDto(
    String accessToken
) {
}

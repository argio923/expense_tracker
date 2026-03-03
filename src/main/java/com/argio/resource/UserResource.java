package com.argio.resource;

import com.argio.dto.user.PasswordUpdateDto;
import com.argio.service.UserService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * REST resource for managing user-related operations.
 * This resource exposes endpoints under the path "/users" and supports operations
 * such as retrieving authenticated user details and updating user passwords.
 *
 * The resource consumes and produces JSON payloads and requires authenticated access.
 */
@Path("/users")
@Authenticated
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject UserService service;

    /**
     * Retrieves the authenticated user's details.
     *
     * This method is used to fetch information about the currently authenticated user.
     * The user's details include their unique ID, email, role, and account creation timestamp.
     * The response body is returned in JSON format.
     *
     * @return a {@link Response} object containing the authenticated user's details
     *         wrapped in a {@link com.argio.dto.user.UserResponseDto}.
     */
    @GET
    @Path("/me")
    public Response getMe() {
        var responseBody = service.getUserByEmail();

        return Response.ok(responseBody).build();
    }

    /**
     * Updates the password of the currently authenticated user.
     * This endpoint validates the user's old password and updates it with a new password
     * if the validation succeeds. The new password must meet the required constraints.
     *
     * @param user a {@link PasswordUpdateDto} object containing:
     *             - oldPassword: the current password of the user, which must match the stored password.
     *             - newPassword: the new password to be set, which must:
     *               - Be between 8 and 16 characters.
     *               - Include at least one lowercase letter, one uppercase letter, one number,
     *                 and one special character.
     * @return a {@link Response} object containing the updated user details wrapped in a
     *         {@link com.argio.dto.user.UserResponseDto}.
     * @throws jakarta.validation.ConstraintViolationException if the provided passwords fail
     *         to meet validation constraints.
     * @throws UserNotFoundByMailException if no user exists with the email of the authenticated principal.
     * @throws WrongPasswordException if the provided old password does not match the stored password hash for the user.
     */
    @PATCH
    @Path("/password-update")
    public Response passwordUpdate(@Valid PasswordUpdateDto user) {
        var responseBody = service.userPasswordUpdate(user);

        return Response.ok(responseBody).build();
    }
}
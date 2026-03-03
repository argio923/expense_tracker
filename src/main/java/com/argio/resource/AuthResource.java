package com.argio.resource;

import com.argio.dto.user.LoginAndRegisterUserDto;
import com.argio.service.AuthService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * AuthResource is a RESTful resource class that provides endpoints for user authentication operations.
 * The endpoints handle user registration and login functionality.
 * Requests and responses are exchanged in JSON format.
 *
 * This resource is open to all users.
 *
 * An instance of this class relies on the {@link AuthService} to process business logic for
 * authentication and user registration.
 */
@Path("/users")
@PermitAll
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject AuthService service;

    /**
     * Registers a new user in the application.
     *
     * @param user a data transfer object containing the user's email and password.
     *             The email must be a valid email format, and the password must
     *             meet the following criteria:
     *             - Between 8 and 16 characters.
     *             - Include at least one lowercase letter, one uppercase letter,
     *               one number, and one special character.
     * @return a Response containing a {@link com.argio.dto.user.UserResponseDto} object with the
     *         details of the newly registered user, including their unique ID,
     *         email, role, and account creation timestamp.
     */
    @POST
    @Path("/new-user")
    public Response userRegistration(@Valid LoginAndRegisterUserDto user) {
        var responseBody = service.userRegistration(user);

        return Response.ok(responseBody).build();
    }

    /**
     * Authenticates a user and returns a session token upon successful login.
     * The user's credentials are validated and if correct, a token is generated.
     *
     * @param user a data transfer object containing the user's email and password.
     *             The email must be a valid email address. The password must:
     *             - Be between 8 and 16 characters long.
     *             - Include at least one lowercase letter, one uppercase letter,
     *               one number, and one special character.
     * @return a Response containing a {@link com.argio.dto.LoginResponseDto} object
     *         which includes the access token for the authenticated user.
     */
    @POST
    @Path("/login")
    public Response login(@Valid LoginAndRegisterUserDto user) {
        var responseBody = service.login(user);

        return Response.ok(responseBody).build();
    }
}
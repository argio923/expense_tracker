package com.argio.service;

import com.argio.dto.LoginResponseDto;
import com.argio.dto.user.LoginAndRegisterUserDto;
import com.argio.dto.user.UserResponseDto;
import com.argio.security.PasswordEncoder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Objects;

/**
 * Provides authentication services, handling user login and registration processes.
 * This service acts as a facade that uses underlying services such as {@code UserService}
 * and {@code PasswordEncoder} to perform authentication-related operations. Methods are
 * transactional to ensure consistency in database operations.
 *
 * Dependencies:
 * - {@code PasswordEncoder}: Used for encoding passwords.
 * - {@code UserService}: Handles user-related actions, such as login validation
 *   and user registration.
 */
@ApplicationScoped
public class AuthService {

    @Inject PasswordEncoder encoder;
    @Inject UserService userService;

    /**
     * Authenticates a user based on the provided login credentials and returns an access token if the login is successful.
     * The method validates the user's email and password, and generates a token for authorized access to the application.
     *
     * @param dto an object containing the user's login credentials, including:
     *            - {@code email}: the user's email address, which must be valid and not null.
     *            - {@code password}: the user's password, which must be between 8 and 16 characters and meet
     *              the complexity requirements (at least one lowercase letter, one uppercase letter, one number,
     *              and one special character).
     * @return a {@link LoginResponseDto} object containing the access token for the authenticated user.
     * @throws NullPointerException if the {@code dto} argument is null.
     * @throws com.argio.exception.model.UserNotFoundByMailException if no user exists with the provided email.
     * @throws com.argio.exception.model.WrongPasswordException if the provided password does not match the stored password for the user.
     */
    @Transactional
    public LoginResponseDto login(LoginAndRegisterUserDto dto) {
        Objects.requireNonNull(dto);

        return userService.login(dto);
    }

    /**
     * Registers a new user in the system by creating an account with the provided email and password.
     * The method hashes the password before persisting the user data to ensure security.
     *
     * @param dto an object containing the user's registration details:
     *            - {@code email}: the user's email address, which must be valid and not null.
     *            - {@code password}: the user's password, which must be between 8 and 16 characters
     *              and meet the specified complexity requirements (at least one lowercase letter, one
     *              uppercase letter, one number, and one special character).
     * @return a {@link UserResponseDto} object containing the details of the newly registered user,
     *         including their unique ID, email, role, and account creation timestamp.
     * @throws NullPointerException if the {@code dto} argument is null.
     */
    @Transactional
    public UserResponseDto userRegistration(LoginAndRegisterUserDto dto) {
        Objects.requireNonNull(dto);
        var encodedPass = encoder.hash(dto.password());
        return userService.userRegistration(dto.email(), encodedPass);
    }
}
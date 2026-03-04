package com.argio.service;

import com.argio.assembler.UserAssembler;
import com.argio.dto.LoginResponseDto;
import com.argio.dto.user.LoginAndRegisterUserDto;
import com.argio.dto.user.PasswordUpdateDto;
import com.argio.dto.user.UserResponseDto;
import com.argio.entity.User;
import com.argio.exception.model.UserNotFoundByMailException;
import com.argio.exception.model.WrongPasswordException;
import com.argio.repository.UserRepository;
import com.argio.security.PasswordEncoder;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Objects;

/**
 * Service class for managing users. Provides functionalities for user-related
 * operations such as registration, login, retrieving user details, and updating
 * passwords.
 *
 * This class is application-scoped and leverages dependency injection for its components.
 */
@ApplicationScoped
public class UserService {

    @Inject PasswordEncoder encoder;
    @Inject SecurityIdentity securityIdentity;
    @Inject UserAssembler assembler;
    @Inject UserRepository repository;

    /**
     * Retrieves the details of the currently authenticated user using their email address.
     * This method fetches the email associated with the authenticated user's principal and
     * retrieves their corresponding information from the user repository. If no user is found
     * with the given email, an exception is thrown. The user's details are then mapped to a
     * {@link UserResponseDto} and returned.
     *
     * @return a {@link UserResponseDto} containing the authenticated user's details, including
     *         their unique ID, email, role, and account creation timestamp.
     * @throws UserNotFoundByMailException if no user exists with the
     *         email of the authenticated principal.
     */
    @Authenticated
    public UserResponseDto getUserByEmail() {
        var email = securityIdentity.getPrincipal().getName();
        var user = repository.findByEmail(email).orElseThrow(()-> new UserNotFoundByMailException(email));
        var dto = assembler.toResponseDto(user);

        return dto;
    }

    /**
     * Retrieves the entity of the currently authenticated user using their email address.
     * This method fetches the email associated with the authenticated user's principal and
     * retrieves their corresponding entity from the user repository. If no user is found
     * with the given email, an exception is thrown.
     *
     * @return the {@link User} entity associated with the authenticated user's email.
     * @throws UserNotFoundByMailException if no user exists with the email of the authenticated principal.
     */
    @Authenticated
    public User getEntityByEmail() {
        var email = securityIdentity.getPrincipal().getName();
        return repository.findByEmail(email).orElseThrow(()-> new UserNotFoundByMailException(email));
    }

    /**
     * Registers a new user in the system by persisting their email and encoded password.
     * This method creates a new user instance, saves it to the database using the repository,
     * and converts it to a {@link UserResponseDto} for the result.
     *
     * @param email the email address of the user to register; must be unique, non-null, and valid.
     * @param encodedPass the encoded password of the user; must be non-null and securely hashed prior to calling this method.
     * @return a {@link UserResponseDto} containing the details of the newly registered user,
     *         including their unique ID, email, role, and account creation timestamp.
     */
    @Transactional
    public UserResponseDto userRegistration(String email, String encodedPass) {
        var user = new User(email, encodedPass);
        repository.persistUser(user);

        return assembler.toResponseDto(user);
    }

    /**
     * Authenticates a user using their email and password, and returns a JSON Web Token (JWT) if the credentials are valid.
     * This method verifies the user's email, checks the password against the stored hash, and generates a signed JWT token
     * that can be used for subsequent authenticated requests.
     *
     * @param dto a {@link LoginAndRegisterUserDto} containing the user's email and password. The email must belong to
     *            an existing user, and the password must match the corresponding stored hash.
     * @return a {@link LoginResponseDto} containing the JWT access token.
     * @throws UserNotFoundByMailException if no user exists with the given email.
     * @throws WrongPasswordException if the provided password does not match the stored hash for the user.
     */
    @Transactional
    public LoginResponseDto login(LoginAndRegisterUserDto dto) {

        var user = repository.findByEmail(dto.email())
                             .orElseThrow(() -> new UserNotFoundByMailException(dto.email()));

        if(!encoder.verify(dto.password(), user.getPasswordHash()))
            throw new WrongPasswordException();

        String token = Jwt.issuer("expense-tracker")
                          .subject(user.getEmail())
                          .groups(user.getRole().name())
                          .expiresIn(3600) // 1h
                          .sign();
        return new LoginResponseDto(token);
    }

    /**
     * Updates the password of the currently authenticated user.
     * This method validates the old password provided by the user against the stored password hash.
     * If the validation succeeds, the password is updated with a securely hashed version of the new password.
     * The updated user entity is persisted, and a data transfer object (DTO) is returned containing the updated user details.
     *
     * @param dto a {@link PasswordUpdateDto} containing the user's old password and the new password to be set.
     *            The old password must match the stored password hash, and the new password must meet the required constraints.
     * @return a {@link UserResponseDto} containing the updated details of the authenticated user, including their unique ID, email, role, and account creation timestamp.
     * @throws UserNotFoundByMailException if no user exists with the email of the authenticated principal.
     * @throws WrongPasswordException if the provided old password does not match the stored password hash for the user.
     */
    @Authenticated
    @Transactional
    public UserResponseDto userPasswordUpdate(PasswordUpdateDto dto) {
        Objects.requireNonNull(dto);
        var email = securityIdentity.getPrincipal().getName();
        var user = repository.findByEmail(email).orElseThrow(() -> new UserNotFoundByMailException(email));

        if(!encoder.verify(dto.oldPassword(), user.getPasswordHash()))
            throw new WrongPasswordException();

        user.changePassword(encoder.hash(dto.newPassword()));
        repository.persistUser(user);
        return assembler.toResponseDto(user);
    }
}
package com.argio.service;

import com.argio.assembler.UserAssembler;
import com.argio.cache.UserProfileCache;
import com.argio.dto.LoginResponseDto;
import com.argio.dto.user.LoginAndRegisterUserDto;
import com.argio.dto.user.PasswordUpdateDto;
import com.argio.dto.user.UserResponseDto;
import com.argio.entity.User;
import com.argio.exception.model.SecurityIdentityPrincipalException;
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

@ApplicationScoped
public class UserService {

    @Inject PasswordEncoder encoder;
    @Inject SecurityIdentity securityIdentity;
    @Inject UserAssembler assembler;
    @Inject UserRepository repository;

    @Authenticated
    public UserResponseDto getUserByEmail() {
        var email = securityIdentity.getPrincipal().getName();
        var user = repository.findByEmail(email).orElseThrow(()-> new UserNotFoundByMailException(email));
        var dto = assembler.toDto(user);

        return dto;
    }

    @Transactional
    public UserResponseDto userRegistration(LoginAndRegisterUserDto dto) {
        Objects.requireNonNull(dto);
        var encodedPass = encoder.hash(dto.password());
        var user = new User(dto.email(), encodedPass);
        repository.persistUser(user);

        return assembler.toDto(user);
    }

    @Transactional
    public LoginResponseDto login(LoginAndRegisterUserDto dto) {
        Objects.requireNonNull(dto);

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

    @Authenticated
    @Transactional
    public UserResponseDto userPasswordUpdate(PasswordUpdateDto dto) {
        Objects.requireNonNull(dto);
        var user = repository.findByEmail(dto.email()).orElseThrow(() -> new UserNotFoundByMailException(dto.email()));

        if(!encoder.verify(dto.oldPassword(), user.getPasswordHash()))
            throw new WrongPasswordException();

        user.changePassword(encoder.hash(dto.newPassword()));
        repository.persistUser(user);
        return assembler.toDto(user);
    }
}
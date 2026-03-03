package com.argio.exception.model;

public class UserNotFoundByMailException extends DomainException {
    public UserNotFoundByMailException(String email) {
        super("User with email '%s' not found".formatted(email));
    }
}

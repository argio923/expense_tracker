package com.argio.exception.model;

public class WrongPasswordException extends DomainException {
    public WrongPasswordException(String message, Throwable cause) {
        super("Wrong password. Please try again.", cause);
    }

    public WrongPasswordException() {
        super("Wrong password. Please try again.");
    }
}

package com.argio.exception.model;

public class SecurityIdentityPrincipalException extends DomainException {
    public SecurityIdentityPrincipalException() {
        super("Principal not found");
    }

    public SecurityIdentityPrincipalException(Throwable cause) {
        super("Principal not found", cause);
    }
}
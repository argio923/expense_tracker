package com.argio.security;

import com.argio.config.PasswordEncodedConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Objects;

@ApplicationScoped
public class PasswordEncoder {

    public static final Integer WORK_FACTORY = 12;

    public String hash(String rawPassword) {
        Objects.requireNonNull(rawPassword);
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt(WORK_FACTORY));
    }

    public boolean verify(String rawPassword, String hashedPassword) {
        Objects.requireNonNull(rawPassword);
        Objects.requireNonNull(hashedPassword);
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
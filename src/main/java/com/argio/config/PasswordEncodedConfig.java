package com.argio.config;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * Configuration class for setting up properties related to password encoding.
 * Provides configuration for the bcrypt algorithm utilized in password hashing.
 * This class is intended to be used in conjunction with password encoder
 * implementations to define the settings of the bcrypt work factor.
 *
 * The work factor determines the computational complexity of the hashing
 * process, which directly impacts the security and performance characteristics
 * of the password encryption mechanism.
 */
@ApplicationScoped
public class PasswordEncodedConfig {

    @ConfigProperty(name = "bcrypt.work-factory")
    public Integer workFactory;
}
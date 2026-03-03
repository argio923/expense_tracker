package com.argio.config;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class PasswordEncodedConfig {

    @ConfigProperty(name = "bcrypt.work-factory")
    public Integer workFactory;
}
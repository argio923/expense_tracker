package com.argio.repository;

import com.argio.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    /**
     * ricerca un record appartenente alla tabella users
     * @param email chiave di ricerca
     * @return Optional dell'entity corrispondente al record
     */
    public Optional<User> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }

    public boolean existsByMail(String email) {
        return count("email", email) > 0;
    }

    public void persistUser(User user) {
        persist(user);
    }

    public Optional<User> findActiveById(UUID id) {
        return find("id = ?1", id).firstResultOptional();
    }
}
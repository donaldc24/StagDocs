package com.stagdocs.DAO;

import com.stagdocs.model.User;

import java.util.Optional;

public interface UserDAO {
    Optional<User> findByUsername(String username);

    User save(User user);

    void deleteById(Long id);
}

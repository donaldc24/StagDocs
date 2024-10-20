package com.stagdocs.service;

import com.stagdocs.DAO.UserDAO;
import com.stagdocs.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDAO.save(user);
    }

    public void deleteUserById(Long id) {
        userDAO.deleteById(id);
    }

    public boolean isUsernameAvailable(String username) {
        return userDAO.findByUsername(username).isEmpty();
    }
}
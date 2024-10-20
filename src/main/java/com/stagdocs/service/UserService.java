package com.stagdocs.service;

import com.stagdocs.DAO.UserDAO;
import com.stagdocs.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    // Find user by username
    public Optional<User> findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    // Save or update user
    public User saveUser(User user) {
        return userDAO.save(user);
    }

    // Delete user by ID
    public void deleteUserById(Long id) {
        userDAO.deleteById(id);
    }

    // Example additional business logic
    public boolean isUsernameAvailable(String username) {
        return userDAO.findByUsername(username).isEmpty();
    }
}

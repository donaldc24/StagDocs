package com.stagdocs.DAO;

import com.stagdocs.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // SQL queries
    private static final String FIND_BY_USERNAME_SQL = "SELECT * FROM users WHERE username = ?";
    private static final String INSERT_USER_SQL = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
    private static final String UPDATE_USER_SQL = "UPDATE users SET username = ?, password = ?, role = ? WHERE id = ?";
    private static final String DELETE_USER_SQL = "DELETE FROM users WHERE id = ?";

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_USERNAME_SQL, new UserRowMapper(), username));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            // Insert new user
            jdbcTemplate.update(INSERT_USER_SQL, user.getUsername(), user.getPassword(), user.getRole());
        } else {
            // Update existing user
            jdbcTemplate.update(UPDATE_USER_SQL, user.getUsername(), user.getPassword(), user.getRole(), user.getId());
        }
        return user;
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_USER_SQL, id);
    }

    // RowMapper to convert result set rows to User objects
    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));
            return user;
        }
    }
}

package com.Savore.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Savore.config.DbConfig;
import com.Savore.model.UserModel;
import com.Savore.util.PasswordUtil;

/**
 * Service class for handling user login operations.
 */
public class LoginService {

    /**
     * Authenticates a user by verifying username and password against the database.
     *
     * @param username the username provided by the user
     * @param password the password provided by the user
     * @return a UserModel containing user details if authentication is successful,
     *         null if credentials are invalid
     * @throws SQLException           if a database error occurs
     * @throws ClassNotFoundException if the database driver is not found
     */
    public UserModel authenticateUser(String username, String password) throws SQLException, ClassNotFoundException {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            System.err.println("Invalid username or password: username=" + username);
            return null;
        }

        String query = "SELECT username, password, role FROM users WHERE username = ?";
        try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            System.out.println("Executing query: " + query + " with username: " + username);

            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    String dbUserName = result.getString("username");
                    String dbPassword = result.getString("password");
                    String dbRole = result.getString("role");

                    System.out.println("Retrieved username: " + dbUserName + ", role: " + dbRole);
                    System.out.println("Stored hash: " + dbPassword);

                    // Verify password using hash
                    if (PasswordUtil.checkPassword(password, dbPassword)) {
                        UserModel user = new UserModel();
                        user.setUsername(dbUserName);
                        user.setRole(dbRole);
                        System.out.println("Authentication successful for user: " + dbUserName);
                        return user;
                    } else {
                        System.err.println("Password mismatch for user: " + username);
                        return null;
                    }
                } else {
                    System.err.println("User not found: " + username);
                    return null;
                }
            }
        }
    }
}
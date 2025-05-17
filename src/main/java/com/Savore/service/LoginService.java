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
 * Validates credentials and returns authenticated user details.
 * 
 * author: 23048573_ArchanaGiri
 */
public class LoginService {

    /**
     * Authenticates a user by verifying the provided username and password against stored records.
     *
     * @param username the entered username
     * @param password the entered password
     * @return UserModel if credentials are correct, otherwise null
     * @throws SQLException if any SQL error occurs
     * @throws ClassNotFoundException if JDBC driver is not found
     */
    public UserModel authenticateUser(String username, String password) throws SQLException, ClassNotFoundException {
        // Validate input
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            System.out.println("Empty username or password provided.");
            return null;
        }

        String query = "SELECT user_id, username, password, role FROM users WHERE username = ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);

            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    int userId = result.getInt("user_id");
                    String dbUserName = result.getString("username");
                    String dbPassword = result.getString("password");
                    String dbRole = result.getString("role");

                    // Validate password
                    if (PasswordUtil.checkPassword(password, dbPassword)) {
                        UserModel user = new UserModel();
                        user.setUserId(userId);
                        user.setUsername(dbUserName);
                        user.setRole(dbRole);
                        System.out.println("Login successful for user: " + dbUserName + " (Role: " + dbRole + ")");
                        return user;
                    } else {
                        System.out.println("Invalid password for user: " + username);
                        return null;
                    }
                } else {
                    System.out.println("Username not found: " + username);
                    return null;
                }
            }
        }
    }
}

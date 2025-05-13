package com.Savore.service;

import com.Savore.config.DbConfig;
import com.Savore.model.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 * Service class for handling user registration operations, including checking for
 * existing usernames/emails and registering new users in the database.
 */
public class RegisterService {
    private static final Logger LOGGER = Logger.getLogger(RegisterService.class.getName());

    /**
     * Checks if a username already exists in the database.
     *
     * @param username the username to check
     * @return true if the username exists, false otherwise
     */
    public boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.severe("Error checking username existence: " + e.getMessage());
        }
        return false;
    }

    /**
     * Checks if an email already exists in the database.
     *
     * @param email the email to check
     * @return true if the email exists, false otherwise
     */
    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.severe("Error checking email existence: " + e.getMessage());
        }
        return false;
    }

    /**
     * Registers a new user in the database.
     *
     * @param user the UserModel containing user details
     * @return true if registration is successful, false otherwise
     */
    public boolean registerUser(UserModel user) {
        LOGGER.info("Attempting to register user: " + user.getUsername());

        String sql = "INSERT INTO users (username, email, password, address, role, created_at, is_subscribed) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            LOGGER.fine("Executing SQL: " + sql);
            LOGGER.fine("With values: username=" + user.getUsername() + ", email=" + user.getEmail() +
                        ", address=" + user.getAddress() + ", role=" + user.getRole());

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getAddress());
            stmt.setString(5, user.getRole());
            stmt.setTimestamp(6, java.sql.Timestamp.valueOf(user.getCreatedAt()));
            stmt.setBoolean(7, user.getIsSubscribed());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                LOGGER.info("Registration successful for user: " + user.getUsername() + ". Rows affected: " + rowsAffected);
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setUserId(generatedKeys.getInt(1));
                        LOGGER.fine("Generated user ID: " + user.getUserId());
                    }
                }
                return true;
            }
            LOGGER.warning("No rows affected by INSERT for user: " + user.getUsername());
            return false;
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.severe("Error registering user: " + user.getUsername() + ". Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Debug method to check the users table structure (optional, for development use).
     */
    public void debugTableStructure() {
        try (Connection conn = DbConfig.getDbConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("DESCRIBE users");
            LOGGER.info("Users table structure:");
            while (rs.next()) {
                LOGGER.info(rs.getString(1) + " - " + rs.getString(2) + " - " + rs.getString(3));
            }
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.severe("Error checking table structure: " + e.getMessage());
        }
    }
}
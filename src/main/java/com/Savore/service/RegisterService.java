package com.Savore.service;

import com.Savore.config.DbConfig;
import com.Savore.model.UserModel;

import java.sql.*;

/**
 * Service class for handling user registration operations,
 * including checking for existing usernames/emails and adding new users to the database.
 * 
 * author: 23048573_ArchanaGiri
 */
public class RegisterService {

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
                    boolean exists = rs.getInt(1) > 0;
                    System.out.println("Username '" + username + "' exists: " + exists);
                    return exists;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error checking username existence: " + e.getMessage());
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
                    boolean exists = rs.getInt(1) > 0;
                    System.out.println("Email '" + email + "' exists: " + exists);
                    return exists;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error checking email existence: " + e.getMessage());
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
        System.out.println("Attempting to register user: " + user.getUsername());

        String sql = "INSERT INTO users (username, email, password, address, role, created_at, Image_URL) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getAddress());
            stmt.setString(5, user.getRole());
            stmt.setTimestamp(6, Timestamp.valueOf(user.getCreatedAt()));
            stmt.setString(7, user.getImage_URL());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Registration successful for: " + user.getUsername());

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setUserId(generatedKeys.getInt(1));
                        System.out.println("Generated user ID: " + user.getUserId());
                    }
                }
                return true;
            }

            System.out.println("Registration failed, no rows affected for: " + user.getUsername());
            return false;

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error registering user '" + user.getUsername() + "': " + e.getMessage());
            return false;
        }
    }

    /**
     * Debug method to print the structure of the users table.
     * Useful for troubleshooting and development.
     */
    public void debugTableStructure() {
        try (Connection conn = DbConfig.getDbConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("DESCRIBE users");
            System.out.println("Users table structure:");
            while (rs.next()) {
                System.out.println(" - " + rs.getString(1) + " : " + rs.getString(2) + " (" + rs.getString(3) + ")");
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error describing table structure: " + e.getMessage());
        }
    }
}

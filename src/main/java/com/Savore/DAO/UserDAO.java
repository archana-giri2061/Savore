package com.Savore.DAO;

import com.Savore.model.UserModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for performing database operations related to users.
 * Handles user retrieval, updates, and authentication-related actions.
 * 
 * author: 23048573_ArchanaGiri
 */
public class UserDAO {

    /**
     * Establishes and returns a database connection.
     */
    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/savore_db"; // Change as needed
        String dbUser = "root";
        String dbPassword = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found.");
            e.printStackTrace();
        }

        return DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
    }

    /**
     * Retrieves a user based on their email.
     */
    public UserModel getUserByEmail(String email) {
        UserModel user = null;
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
                System.out.println("User found with email: " + email);
            }
        } catch (SQLException e) {
            System.err.println("Failed to get user by email.");
            e.printStackTrace();
        }

        return user;
    }

    /**
     * Retrieves a user based on their ID.
     */
    public UserModel getUserById(int userId) {
        UserModel user = null;
        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
                System.out.println("User found with ID: " + userId);
            }
        } catch (SQLException e) {
            System.err.println("Failed to get user by ID.");
            e.printStackTrace();
        }

        return user;
    }

    /**
     * Retrieves all users in the system.
     */
    public List<UserModel> getAllUsers() {
        List<UserModel> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(extractUser(rs));
            }
            System.out.println("Retrieved all users: " + users.size());
        } catch (SQLException e) {
            System.err.println("Failed to get all users.");
            e.printStackTrace();
        }

        return users;
    }

    /**
     * Retrieves all users with the role 'ADMIN'.
     */
    public List<UserModel> getAllAdmins() {
        List<UserModel> adminList = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = 'ADMIN'";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                adminList.add(extractUser(rs));
            }
            System.out.println("Retrieved admin users: " + adminList.size());
        } catch (SQLException e) {
            System.err.println("Failed to get admin users.");
            e.printStackTrace();
        }

        return adminList;
    }

    /**
     * Updates basic user information, including profile image.
     */
    public void updateUser(UserModel user) {
        String sql = "UPDATE users SET username = ?, email = ?, address = ?, Image_URL = ? WHERE user_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getAddress());
            stmt.setString(4, user.getImage_URL());
            stmt.setInt(5, user.getUserId());

            stmt.executeUpdate();
            System.out.println("User updated: " + user.getUsername());
        } catch (SQLException e) {
            System.err.println("Failed to update user.");
            e.printStackTrace();
        }
    }

    /**
     * Updates password by user model.
     */
    public void updatePassword(UserModel user) {
        String sql = "UPDATE users SET password = ? WHERE user_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getPassword());
            ps.setInt(2, user.getUserId());
            ps.executeUpdate();
            System.out.println("Password updated for user ID: " + user.getUserId());
        } catch (SQLException e) {
            System.err.println("Failed to update password by user ID.");
            e.printStackTrace();
        }
    }

    /**
     * Updates password by email (used in password recovery or reset flows).
     */
    public void updatePassword(String email, String password) {
        String sql = "UPDATE users SET password = ? WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, password);
            ps.setString(2, email);
            ps.executeUpdate();
            System.out.println("Password updated for email: " + email);
        } catch (SQLException e) {
            System.err.println("Failed to update password by email.");
            e.printStackTrace();
        }
    }

    /**
     * Maps a ResultSet row to a UserModel object.
     */
    private UserModel extractUser(ResultSet rs) throws SQLException {
        UserModel user = new UserModel();
        user.setUserId(rs.getInt("user_id"));
        user.setEmail(rs.getString("email"));
        user.setUsername(rs.getString("username"));
        user.setRole(rs.getString("role"));
        user.setAddress(rs.getString("address"));
        user.setImage_URL(rs.getString("Image_URL"));
        user.setPassword(rs.getString("password")); // optional: used only for login or password change
        return user;
    }
}

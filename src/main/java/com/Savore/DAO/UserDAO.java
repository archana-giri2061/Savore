
package com.Savore.DAO;

import com.Savore.model.UserModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/savore"; // replace with your DB name
        String dbUser = "root"; // replace with your DB username
        String dbPassword = ""; // replace with your DB password

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
    }

    public UserModel getUserByEmail(String email) {
        UserModel user = null;
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new UserModel();
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("name"));
                user.setRole(rs.getString("role"));
                //user.setImagePath(rs.getString("image_path"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public List<UserModel> getAllAdmins() {
        List<UserModel> adminList = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = 'Admin'";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                UserModel user = new UserModel();
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("name"));
                user.setRole(rs.getString("role"));
               // user.setImagePath(rs.getString("image_path"));
                adminList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return adminList;
    }

    public void updatePassword(String email, String password) {
        String sql = "UPDATE users SET password = ? WHERE email = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, password);
            ps.setString(2, email);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

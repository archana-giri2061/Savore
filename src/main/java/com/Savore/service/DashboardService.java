package com.Savore.service;

import com.Savore.config.DbConfig;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DashboardService {

    public Map<String, Object> getDashboardMetrics() throws SQLException, ClassNotFoundException {
        Map<String, Object> metrics = new HashMap<>();

        String totalUsersSQL = "SELECT COUNT(*) FROM users";
        String newUsersSQL = "SELECT COUNT(*) FROM users WHERE created_at >= NOW() - INTERVAL 3 DAY";
        String totalOrdersSQL = "SELECT COUNT(*) FROM orders";
        String totalRevenueSQL = "SELECT SUM(total_amount) FROM orders WHERE status = 'DELIVERED'";

        try (Connection conn = DbConfig.getDbConnection()) {

            try (PreparedStatement ps = conn.prepareStatement(totalUsersSQL);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) metrics.put("totalUsers", rs.getInt(1));
            }

            try (PreparedStatement ps = conn.prepareStatement(newUsersSQL);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) metrics.put("newSignups", rs.getInt(1));
            }

            try (PreparedStatement ps = conn.prepareStatement(totalOrdersSQL);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) metrics.put("totalOrders", rs.getInt(1));
            }

            try (PreparedStatement ps = conn.prepareStatement(totalRevenueSQL);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) metrics.put("totalRevenue", rs.getDouble(1));
            }
        }

        return metrics;
    }
}

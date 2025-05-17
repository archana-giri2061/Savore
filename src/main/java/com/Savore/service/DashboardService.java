package com.Savore.service;

import com.Savore.config.DbConfig;

import java.sql.*;
import java.util.*;

/**
 * Service class that provides aggregated data for the Admin Dashboard.
 * Gathers metrics such as total users, new signups, order stats, revenue, and recent orders.
 * 
 * author: 23048573_ArchanaGiri
 */
public class DashboardService {

    /**
     * Retrieves dashboard metrics: user count, new signups, order stats, and recent orders.
     *
     * @return a Map containing metric key-value pairs
     * @throws SQLException when database access fails
     * @throws ClassNotFoundException when MySQL driver is not found
     */
    public Map<String, Object> getDashboardMetrics() throws SQLException, ClassNotFoundException {
        Map<String, Object> metrics = new HashMap<>();
        Connection conn = null;

        try {
            conn = DbConfig.getDbConnection();
            System.out.println("Dashboard DB connection established.");

            // Total users
            try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM users")) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    metrics.put("totalUsers", rs.getInt(1));
                    System.out.println("Total users: " + rs.getInt(1));
                }
            }

            // New users in last 3 days
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT COUNT(*) FROM users WHERE created_at >= NOW() - INTERVAL 3 DAY")) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    metrics.put("newSignups", rs.getInt(1));
                    System.out.println("New signups (last 3 days): " + rs.getInt(1));
                }
            }

            // Total orders
            try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM orders")) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    metrics.put("totalOrders", rs.getInt(1));
                    System.out.println("Total orders: " + rs.getInt(1));
                }
            }

            // Total revenue for DELIVERED and PENDING orders
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT COALESCE(SUM(total_amount), 0) FROM orders WHERE status IN ('DELIVERED', 'PENDING')")) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    double revenue = rs.getDouble(1);
                    metrics.put("totalRevenue", revenue);
                    System.out.println("Total revenue: " + revenue);
                } else {
                    metrics.put("totalRevenue", 0.0);
                    System.out.println("Total revenue: 0.0");
                }
            }

            // Recent 10 orders (joined with users, food items)
            String sql = """
                SELECT o.order_id, u.username, f.food_name, oi.quantity, oi.price, o.total_amount, o.order_date, o.status
                FROM orders o
                JOIN users u ON o.user_id = u.user_id
                JOIN order_items oi ON o.order_id = oi.order_id
                JOIN food_items f ON oi.food_id = f.food_id
                ORDER BY o.order_date DESC
                LIMIT 10
            """;

            List<Map<String, Object>> recentOrders = new ArrayList<>();

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Map<String, Object> order = new LinkedHashMap<>();
                    order.put("orderId", rs.getInt("order_id"));
                    order.put("username", rs.getString("username"));
                    order.put("foodName", rs.getString("food_name"));
                    order.put("quantity", rs.getInt("quantity"));
                    order.put("unitPrice", rs.getDouble("price"));
                    order.put("amount", rs.getDouble("total_amount"));
                    order.put("orderDate", rs.getTimestamp("order_date"));
                    order.put("status", rs.getString("status"));
                    recentOrders.add(order);
                }
                System.out.println("Recent orders fetched: " + recentOrders.size());
            }

            metrics.put("recentOrders", recentOrders);

        } finally {
            if (conn != null) {
                conn.close();
                System.out.println("Dashboard DB connection closed.");
            }
        }

        return metrics;
    }
}

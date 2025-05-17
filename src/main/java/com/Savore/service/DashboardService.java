package com.Savore.service;

import com.Savore.config.DbConfig;

import java.sql.*;
import java.util.*;

public class DashboardService {

    public Map<String, Object> getDashboardMetrics() throws SQLException, ClassNotFoundException {
        Map<String, Object> metrics = new HashMap<>();
        Connection conn = null;

        try {
            conn = DbConfig.getDbConnection();

            // Total users
            try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM users")) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) metrics.put("totalUsers", rs.getInt(1));
            }

            // New users last 3 days
            try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM users WHERE created_at >= NOW() - INTERVAL 3 DAY")) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) metrics.put("newSignups", rs.getInt(1));
            }

            // Total orders
            try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM orders")) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) metrics.put("totalOrders", rs.getInt(1));
            }

            // ✅ Total revenue including both DELIVERED and PENDING
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT COALESCE(SUM(total_amount), 0) FROM orders WHERE status IN ('DELIVERED', 'PENDING')"
            )) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    double revenue = rs.getDouble(1);
                    metrics.put("totalRevenue", revenue);
                } else {
                    metrics.put("totalRevenue", 0.0);
                }
            }

            // ✅ Recent Orders (limit to 10)
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
                    order.put("orderDate", rs.getTimestamp("order_date"));  // key renamed to match JSP
                    order.put("status", rs.getString("status"));
                    recentOrders.add(order);
                }
            }

            metrics.put("recentOrders", recentOrders);

        } finally {
            if (conn != null) conn.close();  // ❗ Now we close only after all queries are done
        }

        return metrics;
    }
}

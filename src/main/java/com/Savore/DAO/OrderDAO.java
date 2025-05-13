package com.Savore.DAO;

import com.Savore.config.DbConfig;
import com.Savore.model.OrderModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public int getTotalOrderCount() throws ClassNotFoundException {
        String query = "SELECT COUNT(*) FROM Orders";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get total order count", e);
        }
        return 0;
    }

    public int getTotalOrderCountPreviousPeriod() throws ClassNotFoundException {
        String query = "SELECT COUNT(*) FROM Orders WHERE order_date < DATE_SUB(CURDATE(), INTERVAL 7 DAY)";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get previous period order count", e);
        }
        return 0;
    }

    public double getTotalRevenue() throws ClassNotFoundException {
        String query = "SELECT SUM(total_amount) FROM Orders";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get total revenue", e);
        }
        return 0.0;
    }

    public double getTotalRevenuePreviousPeriod() throws ClassNotFoundException {
        String query = "SELECT SUM(total_amount) FROM Orders WHERE order_date < DATE_SUB(CURDATE(), INTERVAL 7 DAY)";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get previous period revenue", e);
        }
        return 0.0;
    }

    public List<OrderModel> getRecentOrders(int limit) throws ClassNotFoundException {
        String query = "SELECT o.order_id, o.user_id, u.username, o.order_date, o.status, o.total_amount, " +
                      "GROUP_CONCAT(f.name SEPARATOR ', ') as food_items " +
                      "FROM Orders o " +
                      "JOIN Users u ON o.user_id = u.user_id " +
                      "LEFT JOIN Order_Items oi ON o.order_id = oi.order_id " +
                      "LEFT JOIN Food f ON oi.food_id = f.food_id " +
                      "GROUP BY o.order_id " +
                      "ORDER BY o.order_date DESC LIMIT ?";
        List<OrderModel> orders = new ArrayList<>();
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, limit);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderModel order = new OrderModel();
                    order.setOrderId(rs.getInt("order_id"));
                    order.setUserId(rs.getInt("user_id"));
                    order.setUserName(rs.getString("username"));
                    order.setFoodItems(rs.getString("food_items"));
                    order.setOrderDate(rs.getTimestamp("order_date"));
                    order.setStatus(rs.getString("status"));
                    order.setTotalAmount(rs.getDouble("total_amount"));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get recent orders", e);
        }
        return orders;
    }
}
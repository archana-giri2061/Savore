package com.Savore.DAO;

import com.Savore.config.DbConfig;
import com.Savore.model.OrderModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class to handle operations related to the Orders table.
 * Includes methods to retrieve total orders, revenue, and recent orders.
 * 
 * author: 23048573_ArchanaGiri
 */
public class OrderDAO {

    /**
     * Retrieves total number of orders in the system.
     */
    public int getTotalOrderCount() throws ClassNotFoundException {
        String query = "SELECT COUNT(*) FROM Orders";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Total order count: " + count);
                return count;
            }
        } catch (SQLException e) {
            System.err.println("Failed to get total order count");
            throw new RuntimeException(e);
        }
        return 0;
    }

    /**
     * Retrieves total number of orders placed more than 7 days ago.
     */
    public int getTotalOrderCountPreviousPeriod() throws ClassNotFoundException {
        String query = "SELECT COUNT(*) FROM Orders WHERE order_date < DATE_SUB(CURDATE(), INTERVAL 7 DAY)";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Previous period order count: " + count);
                return count;
            }
        } catch (SQLException e) {
            System.err.println("Failed to get previous period order count");
            throw new RuntimeException(e);
        }
        return 0;
    }

    /**
     * Retrieves total revenue generated from all orders.
     */
    public double getTotalRevenue() throws ClassNotFoundException {
        String query = "SELECT SUM(total_amount) FROM Orders";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                double revenue = rs.getDouble(1);
                System.out.println("Total revenue: " + revenue);
                return revenue;
            }
        } catch (SQLException e) {
            System.err.println("Failed to get total revenue");
            throw new RuntimeException(e);
        }
        return 0.0;
    }

    /**
     * Retrieves total revenue generated from orders placed more than 7 days ago.
     */
    public double getTotalRevenuePreviousPeriod() throws ClassNotFoundException {
        String query = "SELECT SUM(total_amount) FROM Orders WHERE order_date < DATE_SUB(CURDATE(), INTERVAL 7 DAY)";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                double revenue = rs.getDouble(1);
                System.out.println("Previous period revenue: " + revenue);
                return revenue;
            }
        } catch (SQLException e) {
            System.err.println("Failed to get previous period revenue");
            throw new RuntimeException(e);
        }
        return 0.0;
    }

    /**
     * Retrieves the most recent orders with user info and grouped food items.
     * 
     * @param limit maximum number of records to fetch
     * @return List of recent orders
     */
    public List<OrderModel> getRecentOrders(int limit) throws ClassNotFoundException {
        String query = """
            SELECT o.order_id, o.user_id, u.username, o.order_date, o.status, o.total_amount,
                   GROUP_CONCAT(f.name SEPARATOR ', ') as food_items
            FROM Orders o
            JOIN Users u ON o.user_id = u.user_id
            LEFT JOIN Order_Items oi ON o.order_id = oi.order_id
            LEFT JOIN Food f ON oi.food_id = f.food_id
            GROUP BY o.order_id
            ORDER BY o.order_date DESC
            LIMIT ?
        """;

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
                System.out.println("Retrieved " + orders.size() + " recent orders.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to get recent orders");
            throw new RuntimeException(e);
        }

        return orders;
    }
}

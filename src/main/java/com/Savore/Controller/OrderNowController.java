package com.Savore.Controller;

import com.Savore.config.DbConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;

/**
 * Controller to handle food order form submission and persistence.
 * 
 * Handles:
 * - GET: Loads the order form.
 * - POST: Processes and saves an order to the database.
 * 
 * author: 23048573_ArchanaGiri
 */
@WebServlet("/orderForm")
public class OrderNowController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Displays the order form (GET request).
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Order form accessed.");
        request.getRequestDispatcher("/WEB-INF/pages/orderForm.jsp").forward(request, response);
    }

    /**
     * Handles order submission (POST request).
     * Saves order and associated order items to the database using transaction management.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve session and user ID
        HttpSession session = request.getSession(false);
        Integer userId = (session != null) ? (Integer) session.getAttribute("userId") : null;

        if (userId == null) {
            System.out.println("Unauthorized order attempt. Redirecting to login.");
            response.sendRedirect(request.getContextPath() + "/logIn");
            return;
        }

        try {
            // Extract form data
            int foodId = Integer.parseInt(request.getParameter("foodId"));
            double foodPrice = Double.parseDouble(request.getParameter("foodPrice"));
            String deliveryAddress = request.getParameter("deliveryAddress");
            int quantity = 1; // Can be dynamic in future

            System.out.println("Processing order: foodId=" + foodId + ", userId=" + userId);

            // Start DB connection and transaction
            try (Connection conn = DbConfig.getDbConnection()) {
                conn.setAutoCommit(false); // Begin transaction

                // Insert order record
                String orderSQL = "INSERT INTO orders (user_id, total_amount, delivery_address) VALUES (?, ?, ?)";
                try (PreparedStatement orderStmt = conn.prepareStatement(orderSQL, Statement.RETURN_GENERATED_KEYS)) {
                    orderStmt.setInt(1, userId);
                    orderStmt.setDouble(2, foodPrice * quantity);
                    orderStmt.setString(3, deliveryAddress);
                    orderStmt.executeUpdate();

                    // Retrieve generated order ID
                    ResultSet generatedKeys = orderStmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int orderId = generatedKeys.getInt(1);
                        System.out.println("Order created. Order ID: " + orderId);

                        // Insert order item details
                        String itemSQL = "INSERT INTO order_items (order_id, food_id, quantity, price) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement itemStmt = conn.prepareStatement(itemSQL)) {
                            itemStmt.setInt(1, orderId);
                            itemStmt.setInt(2, foodId);
                            itemStmt.setInt(3, quantity);
                            itemStmt.setDouble(4, foodPrice);
                            itemStmt.executeUpdate();
                        }

                        conn.commit(); // Commit transaction
                        System.out.println("Order and items saved successfully.");
                        response.sendRedirect(request.getContextPath() + "/UserProfile");

                    } else {
                        conn.rollback(); // Rollback if order ID not retrieved
                        System.err.println("Failed to retrieve order ID. Transaction rolled back.");
                        throw new SQLException("Failed to retrieve order ID.");
                    }
                } catch (SQLException e) {
                    conn.rollback(); // Ensure rollback on any failure
                    System.err.println("Database error during order save. Transaction rolled back.");
                    throw new ServletException("Database error: " + e.getMessage(), e);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new ServletException("JDBC Driver not found: " + e.getMessage(), e);
            }

        } catch (NumberFormatException | SQLException e) {
            System.err.println("Error parsing or saving order: " + e.getMessage());
            throw new ServletException("Error processing order: " + e.getMessage(), e);
        }
    }
}

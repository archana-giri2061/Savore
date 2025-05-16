package com.Savore.Controller;

import com.Savore.config.DbConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;

@WebServlet("/orderForm")
public class OrderNowController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    request.getRequestDispatcher("/WEB-INF/pages/orderForm.jsp").forward(request, response);
	}

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Integer userId = (session != null) ? (Integer) session.getAttribute("userId") : null;

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/logIn");
            return;
        }

        try {
            int foodId = Integer.parseInt(request.getParameter("foodId"));
            double foodPrice = Double.parseDouble(request.getParameter("foodPrice"));
            String deliveryAddress = request.getParameter("deliveryAddress");
            int quantity = 1; // or parse from request if dynamic

            try (Connection conn = DbConfig.getDbConnection()) {
                conn.setAutoCommit(false); // Begin transaction

                // Insert into orders table
                String orderSQL = "INSERT INTO orders (user_id, total_amount, delivery_address) VALUES (?, ?, ?)";
                try (PreparedStatement orderStmt = conn.prepareStatement(orderSQL, Statement.RETURN_GENERATED_KEYS)) {
                    orderStmt.setInt(1, userId);
                    orderStmt.setDouble(2, foodPrice * quantity);
                    orderStmt.setString(3, deliveryAddress);
                    orderStmt.executeUpdate();

                    ResultSet generatedKeys = orderStmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int orderId = generatedKeys.getInt(1);

                        // Insert into order_items table
                        String itemSQL = "INSERT INTO order_items (order_id, food_id, quantity, price) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement itemStmt = conn.prepareStatement(itemSQL)) {
                            itemStmt.setInt(1, orderId);
                            itemStmt.setInt(2, foodId);
                            itemStmt.setInt(3, quantity);
                            itemStmt.setDouble(4, foodPrice);
                            itemStmt.executeUpdate();
                        }

                        conn.commit(); // Commit transaction
                        response.sendRedirect(request.getContextPath() + "/UserProfile");
                    } else {
                        conn.rollback(); // Rollback on failure
                        throw new SQLException("Failed to retrieve order ID.");
                    }
                } catch (SQLException e) {
                    conn.rollback(); // Ensure rollback on error
                    throw new ServletException("Database error: " + e.getMessage(), e);
                }
            } catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        } catch (NumberFormatException | SQLException e) {
            throw new ServletException("Error processing order: " + e.getMessage(), e);
        }
    }

}

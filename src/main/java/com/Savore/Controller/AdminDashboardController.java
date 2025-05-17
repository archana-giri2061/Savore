// AdminDashboardController.java
package com.Savore.Controller;

import com.Savore.service.DashboardService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

/**
 * Servlet controller for handling admin dashboard data display.
 * It retrieves system metrics such as total users, revenue, orders, etc.
 * Access is restricted to authenticated admins only.
 * 
 * author: 23048573_ArchanaGiri
 */
@WebServlet(urlPatterns = {"/AdminDashboard"})
public class AdminDashboardController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private DashboardService dashboardService;

    /**
     * Initialize the DashboardService when servlet starts.
     */
    @Override
    public void init() throws ServletException {
        dashboardService = new DashboardService();
        System.out.println("DashboardService initialized.");
    }

    /**
     * Handles GET requests to /AdminDashboard
     * Ensures the user is an admin, retrieves metrics and forwards to JSP.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        // Retrieve session, but don't create a new one if not exists
        HttpSession session = req.getSession(false);

        // Check if the session is valid and user is an admin
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            System.out.println("Unauthorized access to Admin Dashboard. Redirecting to login.");
            resp.sendRedirect(req.getContextPath() + "/logIn");
            return;
        }

        try {
            // Fetch dashboard metrics from the service layer
            Map<String, Object> metrics = dashboardService.getDashboardMetrics();
            System.out.println("Dashboard metrics loaded successfully.");

            // Set attributes to be used in the JSP page
            req.setAttribute("totalUsers", metrics.get("totalUsers"));
            req.setAttribute("newSignups", metrics.get("newSignups"));
            req.setAttribute("totalOrders", metrics.get("totalOrders"));
            req.setAttribute("totalRevenue", metrics.get("totalRevenue"));
            req.setAttribute("recentOrders", metrics.get("recentOrders"));

        } catch (SQLException | ClassNotFoundException e) {
            // Handle errors gracefully
            req.setAttribute("error", "Failed to load dashboard data.");
            System.out.println("❌ Error retrieving dashboard metrics: " + e.getMessage());
            e.printStackTrace();
        }

        // Forward to the admin dashboard view
        req.getRequestDispatcher("/WEB-INF/pages/AdminDashboard.jsp").forward(req, resp);
    }
}

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

@WebServlet(urlPatterns = {"/AdminDashboard"})
public class AdminDashboardController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private DashboardService dashboardService;

    @Override
    public void init() throws ServletException {
        dashboardService = new DashboardService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            resp.sendRedirect(req.getContextPath() + "/logIn");
            return;
        }

        try {
            Map<String, Object> metrics = dashboardService.getDashboardMetrics();

            req.setAttribute("totalUsers", metrics.get("totalUsers"));
            req.setAttribute("newSignups", metrics.get("newSignups"));
            req.setAttribute("totalOrders", metrics.get("totalOrders"));
            req.setAttribute("totalRevenue", metrics.get("totalRevenue"));
            req.setAttribute("recentOrders", metrics.get("recentOrders"));

        } catch (SQLException | ClassNotFoundException e) {
            req.setAttribute("error", "Failed to load dashboard data.");
            e.printStackTrace();
        }

        req.getRequestDispatcher("/WEB-INF/pages/AdminDashboard.jsp").forward(req, resp);
    }
}

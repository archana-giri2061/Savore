package com.Savore.Controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.Savore.service.DashboardService;

/**
 * Controller for admin dashboard view.
 */
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

        // You can set data here if needed
        req.setAttribute("dashboardSummary", dashboardService.getAdminSummary());

        req.getRequestDispatcher("/WEB-INF/pages/AdminDashboard.jsp").forward(req, resp);
        
    }
    
}

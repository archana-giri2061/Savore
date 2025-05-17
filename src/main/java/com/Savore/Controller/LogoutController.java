package com.Savore.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller for handling user logout view.
 * This only forwards to the logout confirmation page (Logout.jsp).
 * 
 * author: 23048573_ArchanaGiri
 */
@WebServlet("/Logout")
public class LogoutController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests to /Logout.
     * Forwards to the Logout.jsp page which may show a goodbye message.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("User reached logout confirmation page.");
        request.getRequestDispatcher("/WEB-INF/pages/Logout.jsp").forward(request, response);
    }
}

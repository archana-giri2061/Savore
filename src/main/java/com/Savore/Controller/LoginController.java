package com.Savore.Controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.Savore.model.UserModel;
import com.Savore.service.LoginService;
import com.Savore.util.SessionUtil;
import com.Savore.util.CookieUtil;

/**
 * Servlet for handling user login requests.
 */
@WebServlet(urlPatterns = {"/logIn"})
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private LoginService loginService;

    @Override
    public void init() throws ServletException {
        this.loginService = new LoginService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String error = (String) session.getAttribute("error");
        if (error != null) {
            req.setAttribute("error", error);
            session.removeAttribute("error");
        }
        req.getRequestDispatcher("/WEB-INF/pages/LogIn.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        // Clear existing session attributes
        session.removeAttribute("userName");
        session.removeAttribute("role");
        session.removeAttribute("admin");

        String identifier = req.getParameter("identifier");
        String password = req.getParameter("password");

        if (identifier == null || identifier.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            session.setAttribute("error", "Username or email and password are required.");
            resp.sendRedirect(req.getContextPath() + "/logIn");
            return;
        }

        try {
            // Authenticate user using identifier (username or email)
            UserModel userModel = loginService.authenticateUser(identifier.trim(), password.trim());

            if (userModel != null) {
                // Set session attributes
                SessionUtil.setAttribute(req, "admin", userModel);
                SessionUtil.setAttribute(req, "userId", userModel.getUserId());
                SessionUtil.setAttribute(req, "userName", userModel.getUsername());
                SessionUtil.setAttribute(req, "role", userModel.getRole());

                // Handle "Remember Me" functionality
                String rememberMe = req.getParameter("rememberMe");
                if ("on".equals(rememberMe)) {
                    String encodedUsername = URLEncoder.encode(userModel.getUsername(), StandardCharsets.UTF_8.toString());
                    CookieUtil.addCookie(resp, "username", encodedUsername, 7 * 24 * 60 * 60); // 7 days
                }

                // Redirect based on role
                String role = userModel.getRole();
                if ("ADMIN".equals(role)) {
                    resp.sendRedirect(req.getContextPath() + "/AdminDashboard");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/home");
                }
            } else {
                session.setAttribute("error", "Invalid username/email or password. Please try again!");
                resp.sendRedirect(req.getContextPath() + "/logIn");
            }
        } catch (Exception e) {
            session.setAttribute("error", "An error occurred during login: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/logIn");
        }
    }
}
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
 * Servlet for handling user login requests for Savoré.
 * Supports authentication, session handling, and "Remember Me" via cookies.
 * 
 * author: 23048573_ArchanaGiri
 */
@WebServlet(urlPatterns = {"/logIn"})
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private LoginService loginService;

    /**
     * Initializes the login service for user authentication.
     */
    @Override
    public void init() throws ServletException {
        this.loginService = new LoginService();
        System.out.println("LoginService initialized.");
    }

    /**
     * Handles GET requests by forwarding to the login form.
     * Displays error message if one was previously set in the session.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String error = (String) session.getAttribute("error");
        if (error != null) {
            req.setAttribute("error", error);
            session.removeAttribute("error");
            System.out.println("Login error displayed: " + error);
        }
        req.getRequestDispatcher("/WEB-INF/pages/LogIn.jsp").forward(req, resp);
    }

    /**
     * Handles POST login attempts. Validates credentials, manages session, and redirects based on role.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // Clear any stale session attributes
        session.removeAttribute("userName");
        session.removeAttribute("role");
        session.removeAttribute("admin");

        String identifier = req.getParameter("identifier");
        String password = req.getParameter("password");

        // Validate form inputs
        if (identifier == null || identifier.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            session.setAttribute("error", "Username or email and password are required.");
            System.out.println("Empty credentials provided.");
            resp.sendRedirect(req.getContextPath() + "/logIn");
            return;
        }

        try {
            // Authenticate user using identifier (username or email)
            UserModel userModel = loginService.authenticateUser(identifier.trim(), password.trim());

            if (userModel != null) {
                // ✅ Successful login — set session attributes
                SessionUtil.setAttribute(req, "loggedInUser", userModel);
                SessionUtil.setAttribute(req, "admin", userModel);
                SessionUtil.setAttribute(req, "userId", userModel.getUserId());
                SessionUtil.setAttribute(req, "userName", userModel.getUsername());
                SessionUtil.setAttribute(req, "role", userModel.getRole());

                System.out.println("User authenticated: " + userModel.getUsername() + " [" + userModel.getRole() + "]");

                // Handle Remember Me functionality via cookie
                String rememberMe = req.getParameter("rememberMe");
                if ("on".equals(rememberMe)) {
                    String encodedUsername = URLEncoder.encode(userModel.getUsername(), StandardCharsets.UTF_8.toString());
                    CookieUtil.addCookie(resp, "username", encodedUsername, 7 * 24 * 60 * 60); // 7 days
                    System.out.println("Remember Me cookie set for user: " + userModel.getUsername());
                }

                // Redirect user based on their role
                if ("ADMIN".equals(userModel.getRole())) {
                    resp.sendRedirect(req.getContextPath() + "/AdminDashboard");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/home");
                }

            } else {
                // ❌ Failed login
                session.setAttribute("error", "Invalid username/email or password. Please try again!");
                System.out.println("Invalid login attempt for: " + identifier);
                resp.sendRedirect(req.getContextPath() + "/logIn");
            }

        } catch (Exception e) {
            // ⚠️ Login error due to exception
            session.setAttribute("error", "An error occurred during login: " + e.getMessage());
            System.err.println("Exception during login: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/logIn");
        }
    }
}

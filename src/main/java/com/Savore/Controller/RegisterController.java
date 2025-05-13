package com.Savore.Controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

import com.Savore.model.UserModel;
import com.Savore.service.RegisterService;
import com.Savore.util.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet for handling user registration requests. Displays the registration form
 * on GET requests and processes form submissions on POST requests, redirecting to
 * the login page upon success.
 */
@WebServlet(urlPatterns = {"/RegistrationPage"})
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    private RegisterService registerService;

    /**
     * Initializes the servlet, creating a new RegisterService instance.
     */
    @Override
    public void init() throws ServletException {
        registerService = new RegisterService();
    }

    /**
     * Handles GET requests by forwarding to the registration page.
     *
     * @param req  the HttpServletRequest object
     * @param resp the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/RegistrationPage.jsp").forward(req, resp);
    }

    /**
     * Handles POST requests for user registration. Validates form data, registers
     * the user, and redirects to the login page on success or redisplays the form
     * with error messages on failure.
     *
     * @param req  the HttpServletRequest object containing form data
     * @param resp the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Extract and validate user info from form
            UserModel user = extractUserModel(req);

            // Call the service to register the user
            boolean isRegistered = registerService.registerUser(user);

            if (isRegistered) {
                HttpSession session = req.getSession();
                session.setAttribute("successMessage", "Registration successful! Please log in.");
                resp.sendRedirect(req.getContextPath() + "/logIn");
            } else {
                StringBuilder errorMsg = new StringBuilder("Registration failed: ");
                if (registerService.isUsernameExists(user.getUsername())) {
                    errorMsg.append("Username already exists. ");
                }
                if (registerService.isEmailExists(user.getEmail())) {
                    errorMsg.append("Email already exists. ");
                }
                if (errorMsg.toString().equals("Registration failed: ")) {
                    errorMsg.append("An unexpected error occurred.");
                }

                req.setAttribute("error", errorMsg.toString());
                req.getRequestDispatcher("/WEB-INF/pages/RegistrationPage.jsp").forward(req, resp);
            }
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/pages/RegistrationPage.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/pages/RegistrationPage.jsp").forward(req, resp);
        }
    }

    /**
     * Extracts user data from the form and creates a UserModel. Validates input
     * fields and hashes the password.
     *
     * @param req the HttpServletRequest containing form data
     * @return a UserModel with the extracted and validated data
     * @throws IllegalArgumentException if validation fails
     */
    private UserModel extractUserModel(HttpServletRequest req) {
        UserModel user = new UserModel();

        // Get form data
        String username = req.getParameter("fullname");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String address = req.getParameter("address");
        String role = req.getParameter("role");

        // Validate inputs
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required.");
        }
        if (username.trim().length() > 50) {
            throw new IllegalArgumentException("Username must not exceed 50 characters.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required.");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required.");
        }
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        if (email.trim().length() > 100) {
            throw new IllegalArgumentException("Email must not exceed 100 characters.");
        }
        if (role == null || role.trim().isEmpty()) {
            throw new IllegalArgumentException("Role is required.");
        }

        // Set basic fields
        user.setUsername(username.trim());

        try {
            // Hash the password
            String hashedPassword = PasswordUtil.hashPassword(password.trim());
            user.setPassword(hashedPassword);
        } catch (Exception e) {
            throw new RuntimeException("Password hashing failed.", e);
        }

        user.setEmail(email.trim());
        user.setAddress(address != null && !address.trim().isEmpty() ? address.trim() : null);

        String normalizedRole = role.trim().toUpperCase();
        if (!normalizedRole.equals("ADMIN") && !normalizedRole.equals("USER")) {
            throw new IllegalArgumentException("Invalid role specified. Must be 'ADMIN' or 'USER'.");
        }
        user.setRole(normalizedRole);

        user.setCreatedAt(LocalDateTime.now());
        user.setIsSubscribed(false);

        return user;
    }
}
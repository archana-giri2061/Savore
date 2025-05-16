
package com.Savore.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

import com.Savore.model.UserModel;
import com.Savore.service.RegisterService;
import com.Savore.util.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet(urlPatterns = {"/RegistrationPage"})
@MultipartConfig(maxFileSize = 1024 * 1024 * 5)
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    private RegisterService registerService;

    @Override
    public void init() throws ServletException {
        registerService = new RegisterService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/RegistrationPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserModel user = extractUserModel(req);
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

    private UserModel extractUserModel(HttpServletRequest req) throws Exception {
        UserModel user = new UserModel();

        String username = req.getParameter("fullname");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String address = req.getParameter("address");
        String role = req.getParameter("role");

        // Handle profile image
        Part imagePart = req.getPart("profileImage");
        String imageFileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();

        // Define the upload directory: /Resources/Images/Profile
        String uploadDirPath = req.getServletContext().getRealPath("/") + "Resources" + File.separator + "Images" + File.separator + "Profile";
        File uploadDir = new File(uploadDirPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        // Save image to disk
        String savedImagePath = uploadDirPath + File.separator + imageFileName;
        imagePart.write(savedImagePath);

        // Validation
        if (username == null || username.trim().isEmpty())
            throw new IllegalArgumentException("Username is required.");
        if (username.trim().length() > 50)
            throw new IllegalArgumentException("Username must not exceed 50 characters.");
        if (password == null || password.trim().isEmpty())
            throw new IllegalArgumentException("Password is required.");
        if (password.length() < 8)
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        if (email == null || email.trim().isEmpty())
            throw new IllegalArgumentException("Email is required.");
        if (!EMAIL_PATTERN.matcher(email.trim()).matches())
            throw new IllegalArgumentException("Invalid email format.");
        if (email.trim().length() > 100)
            throw new IllegalArgumentException("Email must not exceed 100 characters.");
        if (role == null || role.trim().isEmpty())
            throw new IllegalArgumentException("Role is required.");

        // Set values
        user.setUsername(username.trim());
        user.setPassword(PasswordUtil.hashPassword(password.trim()));
        user.setEmail(email.trim());
        user.setAddress(address != null && !address.trim().isEmpty() ? address.trim() : null);
        user.setRole(role.trim().toUpperCase());
        user.setCreatedAt(LocalDateTime.now());
        user.setIsSubscribed(false);

        // ✅ Store only the file name (not full path) in the database
        user.setImage_URL(imageFileName);

        return user;
    }

}

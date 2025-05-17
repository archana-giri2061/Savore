package com.Savore.Controller;

import com.Savore.DAO.UserDAO;
import com.Savore.model.UserModel;
import com.Savore.util.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * Controller to handle admin profile view and updates.
 * Supports updating profile info, profile picture, and changing password.
 * 
 * author: 23048573_ArchanaGiri
 */
@WebServlet("/AdminProfile")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // Max image upload size: 5MB
public class AdminProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserDAO userDAO = new UserDAO();

    /**
     * Handles GET requests to view the admin profile page.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check session and authentication
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            System.out.println("Admin not logged in. Redirecting to login page.");
            request.getRequestDispatcher("/WEB-INF/pages/LogIn.jsp").forward(request, response);
            return;
        }

        UserModel sessionAdmin = (UserModel) session.getAttribute("admin");

        // Refresh admin data from DB
        UserModel updatedAdmin = userDAO.getUserById(sessionAdmin.getUserId());
        session.setAttribute("admin", updatedAdmin);
        request.setAttribute("admin", updatedAdmin);

        // Load all registered users
        List<UserModel> users = userDAO.getAllUsers();
        request.setAttribute("users", users);

        System.out.println("Admin profile and user list loaded.");
        request.getRequestDispatcher("/WEB-INF/pages/AdminProfile.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for profile update or password change.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            System.out.println("Session expired or invalid. Redirecting to login.");
            request.getRequestDispatcher("/WEB-INF/pages/LogIn.jsp").forward(request, response);
            return;
        }

        UserModel sessionAdmin = (UserModel) session.getAttribute("admin");
        String action = request.getParameter("action");

        // 👉 Profile update action
        if ("update".equals(action)) {
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String address = request.getParameter("address");
            Part imagePart = request.getPart("profileImage");

            // Handle image upload if provided
            if (imagePart != null && imagePart.getSize() > 0) {
                String imageFileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
                String imagePath = "Resources/Images/System/UserProfile/" + imageFileName;

                String realPath = request.getServletContext().getRealPath("/") + imagePath;
                File imageFile = new File(realPath);
                imageFile.getParentFile().mkdirs(); // Create directory if it doesn't exist
                imagePart.write(realPath); // Write image to file system

                sessionAdmin.setImage_URL(imageFileName); // Save only the file name to DB
                System.out.println("Profile image uploaded: " + imageFileName);
            }

            // Update other fields
            sessionAdmin.setUsername(username);
            sessionAdmin.setEmail(email);
            sessionAdmin.setAddress(address);

            // Save updates to DB
            userDAO.updateUser(sessionAdmin);
            session.setAttribute("admin", sessionAdmin);
            request.setAttribute("successMessage", "Profile updated successfully.");
            System.out.println("Admin profile updated.");

        } 
        // 👉 Password change action
        else if ("changePassword".equals(action)) {
            String newPassword = request.getParameter("password");

            if (newPassword != null && newPassword.length() >= 8) {
                try {
                    String hashed = PasswordUtil.hashPassword(newPassword);
                    sessionAdmin.setPassword(hashed);
                    userDAO.updatePassword(sessionAdmin.getEmail(), hashed);
                    request.setAttribute("successMessage", "Password changed successfully.");
                    System.out.println("Admin password changed.");
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("errorMessage", "An error occurred while updating the password.");
                    System.out.println("Password update failed.");
                }
            } else {
                request.setAttribute("errorMessage", "Password must be at least 8 characters.");
                System.out.println("Password too short.");
            }
        }

        // Refresh profile and user list
        request.setAttribute("admin", sessionAdmin);
        request.setAttribute("users", userDAO.getAllUsers());
        request.getRequestDispatcher("/WEB-INF/pages/AdminProfile.jsp").forward(request, response);
    }
}

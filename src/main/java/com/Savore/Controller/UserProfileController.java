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

/**
 * Controller responsible for displaying and updating user profile information.
 * Supports:
 * - Viewing user profile details
 * - Updating profile information and profile picture
 * - Changing user password
 *
 * author: 23048573_ArchanaGiri
 */
@WebServlet("/UserProfile")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // Max 5MB file upload
public class UserProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserDAO userDAO = new UserDAO();

    /**
     * Handles GET requests to /UserProfile.
     * Displays the user's profile information.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            System.out.println("Unauthorized profile access. Redirecting to login.");
            request.getRequestDispatcher("/WEB-INF/pages/LogIn.jsp").forward(request, response);
            return;
        }

        UserModel sessionUser = (UserModel) session.getAttribute("loggedInUser");

        // Fetch updated user details from DB
        UserModel updatedUser = userDAO.getUserById(sessionUser.getUserId());

        // Update session and request with latest user data
        session.setAttribute("loggedInUser", updatedUser);
        request.setAttribute("user", updatedUser);

        System.out.println("User profile loaded for: " + updatedUser.getUsername());
        request.getRequestDispatcher("/WEB-INF/pages/UserProfile.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for updating profile info or changing password.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            System.out.println("Unauthorized profile update attempt.");
            request.getRequestDispatcher("/WEB-INF/pages/LogIn.jsp").forward(request, response);
            return;
        }

        UserModel sessionUser = (UserModel) session.getAttribute("loggedInUser");
        String action = request.getParameter("action");

        if ("update".equals(action)) {
            // Handle profile update
            String username = request.getParameter("firstName");
            String email = request.getParameter("email");
            String address = request.getParameter("address");
            Part imagePart = request.getPart("profileImage");

            if (imagePart != null && imagePart.getSize() > 0) {
                String imageFileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
                String imagePath = "Resources/Images/user/" + imageFileName;

                String realPath = request.getServletContext().getRealPath("/") + imagePath;
                File imageFile = new File(realPath);
                imageFile.getParentFile().mkdirs();
                imagePart.write(realPath);

                sessionUser.setImage_URL(imageFileName); // Only file name saved to DB
                System.out.println("New profile image uploaded: " + imageFileName);
            }

            // Update fields
            sessionUser.setUsername(username);
            sessionUser.setEmail(email);
            sessionUser.setAddress(address);

            // Save to DB
            userDAO.updateUser(sessionUser);
            session.setAttribute("loggedInUser", sessionUser);
            request.setAttribute("successMessage", "Profile updated successfully.");
            System.out.println("User profile updated: " + username);

        } else if ("changePassword".equals(action)) {
            // Handle password change
            String newPassword = request.getParameter("password");
            if (newPassword != null && newPassword.length() >= 8) {
                try {
                    String hashed = PasswordUtil.hashPassword(newPassword);
                    sessionUser.setPassword(hashed);
                    userDAO.updatePassword(sessionUser.getEmail(), hashed);
                    request.setAttribute("successMessage", "Password changed successfully.");
                    System.out.println("Password updated for: " + sessionUser.getUsername());
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("errorMessage", "An error occurred while updating the password.");
                    System.out.println("Password update failed: " + e.getMessage());
                }
            } else {
                request.setAttribute("errorMessage", "Password must be at least 8 characters.");
                System.out.println("Password too short.");
            }
        }

        request.setAttribute("user", sessionUser);
        request.getRequestDispatcher("/WEB-INF/pages/UserProfile.jsp").forward(request, response);
    }
}

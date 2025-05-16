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

@WebServlet("/UserProfile")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5)
public class UserProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserDAO userDAO = new UserDAO();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            request.getRequestDispatcher("/WEB-INF/pages/LogIn.jsp").forward(request, response);
            return;
        }

        UserModel sessionUser = (UserModel) session.getAttribute("loggedInUser");

        // ✅ Fetch the latest user details including image from DB
        UserModel updatedUser = userDAO.getUserById(sessionUser.getUserId());

        // ✅ Update session attribute so changes reflect throughout the session
        session.setAttribute("loggedInUser", updatedUser);
        request.setAttribute("user", updatedUser);

        request.getRequestDispatcher("/WEB-INF/pages/UserProfile.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            request.getRequestDispatcher("/WEB-INF/pages/LogIn.jsp").forward(request, response);
            return;
        }

        UserModel sessionUser = (UserModel) session.getAttribute("loggedInUser");
        String action = request.getParameter("action");

        if ("update".equals(action)) {
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

                sessionUser.setImage_URL(imageFileName); // only image name saved in DB
            }

            sessionUser.setUsername(username);
            sessionUser.setEmail(email);
            sessionUser.setAddress(address);

            userDAO.updateUser(sessionUser);
            session.setAttribute("loggedInUser", sessionUser);
            request.setAttribute("successMessage", "Profile updated successfully.");

        } else if ("changePassword".equals(action)) {
            String newPassword = request.getParameter("password");
            if (newPassword != null && newPassword.length() >= 8) {
                try {
                    String hashed = PasswordUtil.hashPassword(newPassword);
                    sessionUser.setPassword(hashed);
                    userDAO.updatePassword(sessionUser.getEmail(), hashed);
                    request.setAttribute("successMessage", "Password changed successfully.");
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("errorMessage", "An error occurred while updating the password.");
                }
            } else {
                request.setAttribute("errorMessage", "Password must be at least 8 characters.");
            }
        }

        request.setAttribute("user", sessionUser);
        request.getRequestDispatcher("/WEB-INF/pages/UserProfile.jsp").forward(request, response);
    }
}

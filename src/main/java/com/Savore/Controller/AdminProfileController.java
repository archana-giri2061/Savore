package com.Savore.Controller;

import com.Savore.DAO.UserDAO;
import com.Savore.model.UserModel;
import com.Savore.util.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@WebServlet("/AdminProfile")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 15 // 15 MB
)
public class AdminProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserModel admin = null;

        if (session != null) {
            admin = (UserModel) session.getAttribute("admin");
            if (admin == null) {
                UserModel user = (UserModel) session.getAttribute("user");
                if (user != null && "ADMIN".equalsIgnoreCase(user.getRole())) {
                    admin = user;
                    session.setAttribute("admin", admin);
                }
            }
        }

        if (admin == null) {
            response.sendRedirect(request.getContextPath() + "/logIn.jsp");
            return;
        }

        // Validate image path
        if (admin.getImage_URL() == null || admin.getImage_URL().isEmpty()) {
            admin.setImage_URL("Resources/Images/System/Profile/DummyImage.png");
        } else {
            String imagePath = request.getServletContext().getRealPath("/") + admin.getImage_URL();
            File imageFile = new File(imagePath);
            if (!imageFile.exists()) {
                admin.setImage_URL("Resources/Images/System/Profile/DummyImage.png");
            }
        }

        List<UserModel> users = userDAO.getAllUsers();
        request.setAttribute("admin", admin);
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/pages/AdminProfile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserModel admin = null;

        if (session != null) {
            admin = (UserModel) session.getAttribute("admin");
            if (admin == null) {
                UserModel user = (UserModel) session.getAttribute("user");
                if (user != null && "ADMIN".equalsIgnoreCase(user.getRole())) {
                    admin = user;
                    session.setAttribute("admin", admin);
                }
            }
        }

        if (admin == null) {
            response.sendRedirect(request.getContextPath() + "/logIn.jsp");
            return;
        }

        String action = request.getParameter("action");
        String message = "";

        if ("update".equals(action)) {
            admin.setUsername(request.getParameter("username"));
            admin.setEmail(request.getParameter("email"));
            admin.setAddress(request.getParameter("address"));

            Part imagePart = request.getPart("profileImage");
            if (imagePart != null && imagePart.getSize() > 0) {
                String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
                String extension = fileName.substring(fileName.lastIndexOf("."));
                String newFileName = "admin_" + admin.getUserId() + "_" + System.currentTimeMillis() + extension;
                String uploadPath = request.getServletContext().getRealPath("/Resources/Images/Profile");
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdirs();
                File imageFile = new File(uploadPath + File.separator + newFileName);
                imagePart.write(imageFile.getAbsolutePath());
                admin.setImage_URL("Resources/Images/Profile/" + newFileName);
            }

            userDAO.updateUser(admin);
            session.setAttribute("admin", admin);
            message = "Profile updated successfully.";

        } else if ("changePassword".equals(action)) {
            String newPassword = request.getParameter("password");
            try {
                admin.setPassword(PasswordUtil.hashPassword(newPassword));
                userDAO.updatePassword(admin);
                message = "Password updated successfully.";
            } catch (Exception e) {
                message = "Error updating password.";
            }
        }

        request.setAttribute("admin", admin);
        request.setAttribute("users", userDAO.getAllUsers());
        request.setAttribute("message", message);
        request.getRequestDispatcher("/WEB-INF/pages/AdminProfile.jsp").forward(request, response);
    }
}

package com.Savore.Controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import com.Savore.model.UserModel;
import com.Savore.service.RegisterService;
import com.Savore.util.ImageUtil;
import com.Savore.util.PasswordUtil;
import com.Savore.util.RedirectionUtil;

@WebServlet(asyncSupported = true, urlPatterns = { "/RegistrationPage" })
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
    maxFileSize = 1024 * 1024 * 10,       // 10MB
    maxRequestSize = 1024 * 1024 * 50     // 50MB
)
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private RegisterService registerService;
    private ImageUtil imageUtil;
    private RedirectionUtil redirectionUtil;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    @Override
    public void init() throws ServletException {
        this.registerService = new RegisterService();
        this.imageUtil = new ImageUtil();
        this.redirectionUtil = new RedirectionUtil();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        redirectionUtil.redirectToPage(req, resp, RedirectionUtil.registerUrl);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserModel user = extractUserModel(req);
            Boolean isAdded = registerService.registerUser(user);

            if (isAdded) {
                if (uploadImage(req, user.getImage_URL())) {
                    redirectionUtil.setMsgAndRedirect(req, resp, "success", "Account created successfully!", RedirectionUtil.loginUrl);
                } else {
                    redirectionUtil.setMsgAndRedirect(req, resp, "error", "Account created but image upload failed.", RedirectionUtil.registerUrl);
                }
            } else {
                redirectionUtil.setMsgAndRedirect(req, resp, "error", "Registration failed. Try again!", RedirectionUtil.registerUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectionUtil.setMsgAndRedirect(req, resp, "error", "An exception occurred during registration.", RedirectionUtil.registerUrl);
        }
    }

    private UserModel extractUserModel(HttpServletRequest req) throws Exception {
        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String address = req.getParameter("address");
        String role = req.getParameter("role");
        
        
        if (fullname == null || fullname.trim().isEmpty())
            throw new IllegalArgumentException("Full name is required.");
        if (email == null || !EMAIL_PATTERN.matcher(email.trim()).matches())
            throw new IllegalArgumentException("Invalid email format.");
        if (password == null || password.trim().length() < 8)
            throw new IllegalArgumentException("Password must be at least 8 characters.");
        if (role == null || role.trim().isEmpty())
            throw new IllegalArgumentException("Role is required.");

        // Encrypt password
        String encryptedPassword = PasswordUtil.hashPassword(password.trim());

        // Handle uploaded image
        Part imagePart = req.getPart("profileImage");
        String originalFileName = imageUtil.getImageNameFromPart(imagePart);
        String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String uniqueFileName = "IMG_" + DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()) + extension;

        // Populate UserModel
        UserModel user = new UserModel();
        user.setUsername(fullname.trim());
        user.setEmail(email.trim());
        user.setPassword(encryptedPassword);
        user.setAddress((address != null && !address.isBlank()) ? address.trim() : null);
        user.setRole(role.trim().toUpperCase());
        user.setCreatedAt(LocalDateTime.now());
        user.setIsSubscribed(false);
        user.setImage_URL(uniqueFileName); // only filename stored in DB

        return user;
    }

    private boolean uploadImage(HttpServletRequest req, String filename) throws IOException, ServletException {
        Part imagePart = req.getPart("profileImage");
        return imageUtil.uploadImage(imagePart, "", ""); // assumes path logic is handled in ImageUtil
    }
}

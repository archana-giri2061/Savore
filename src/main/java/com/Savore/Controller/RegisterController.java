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

/**
 * Controller responsible for handling user registration for Savoré.
 * Validates input, encrypts passwords, handles image uploads, and inserts user into DB.
 * 
 * author: 23048573_ArchanaGiri
 */
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

    // Regex for email validation
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    /**
     * Initializes required services
     */
    @Override
    public void init() throws ServletException {
        this.registerService = new RegisterService();
        this.imageUtil = new ImageUtil();
        this.redirectionUtil = new RedirectionUtil();
        System.out.println("RegisterController initialized.");
    }

    /**
     * Displays the registration page.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        redirectionUtil.redirectToPage(req, resp, RedirectionUtil.registerUrl);
    }

    /**
     * Handles registration form submission.
     * Validates input, encrypts password, and inserts user record.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserModel user = extractUserModel(req);
            System.out.println("Registering user: " + user.getUsername());

            Boolean isAdded = registerService.registerUser(user);

            if (isAdded) {
                // Try uploading profile image
                if (uploadImage(req, user.getImage_URL())) {
                    System.out.println("User registered successfully with image.");
                    redirectionUtil.setMsgAndRedirect(req, resp, "success", "Account created successfully!", RedirectionUtil.loginUrl);
                } else {
                    System.err.println("User registered, but image upload failed.");
                    redirectionUtil.setMsgAndRedirect(req, resp, "error", "Account created but image upload failed.", RedirectionUtil.registerUrl);
                }
            } else {
                System.err.println("Registration failed in DB.");
                redirectionUtil.setMsgAndRedirect(req, resp, "error", "Registration failed. Try again!", RedirectionUtil.registerUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Exception during registration: " + e.getMessage());
            redirectionUtil.setMsgAndRedirect(req, resp, "error", "An exception occurred during registration.", RedirectionUtil.registerUrl);
        }
    }

    /**
     * Extracts and validates form data to populate a UserModel.
     * 
     * @param req HttpServletRequest
     * @return fully populated UserModel
     * @throws Exception for validation errors
     */
    private UserModel extractUserModel(HttpServletRequest req) throws Exception {
        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String address = req.getParameter("address");
        String role = req.getParameter("role");

        // Validation
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

        // Process uploaded image
        Part imagePart = req.getPart("profileImage");
        String originalFileName = imageUtil.getImageNameFromPart(imagePart);
        String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String uniqueFileName = "IMG_" + DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()) + extension;

        // Populate and return user
        UserModel user = new UserModel();
        user.setUsername(fullname.trim());
        user.setEmail(email.trim());
        user.setPassword(encryptedPassword);
        user.setAddress((address != null && !address.isBlank()) ? address.trim() : null);
        user.setRole(role.trim().toUpperCase());
        user.setCreatedAt(LocalDateTime.now());
        user.setIsSubscribed(false);
        user.setImage_URL(uniqueFileName);

        System.out.println("UserModel prepared: " + user.getEmail());
        return user;
    }

    /**
     * Uploads the profile image using ImageUtil.
     * 
     * @param req HttpServletRequest
     * @param filename the target filename
     * @return true if upload was successful
     */
    private boolean uploadImage(HttpServletRequest req, String filename) throws IOException, ServletException {
        Part imagePart = req.getPart("profileImage");
        boolean uploaded = imageUtil.uploadImage(imagePart, "", ""); // assumes imageUtil handles path
        System.out.println(uploaded ? "Image upload succeeded." : "Image upload failed.");
        return uploaded;
    }
}

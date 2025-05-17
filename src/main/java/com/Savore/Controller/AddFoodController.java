package com.Savore.Controller;

import com.Savore.DAO.FoodItemsDAO;
import com.Savore.model.FoodItems;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Servlet controller for adding new food items to the system.
 * Handles both GET (display form) and POST (process form submission with image upload).
 * 
 * author: 23048573_ArchanaGiri
 */
@WebServlet("/AddFood")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,   // 1MB
    maxFileSize = 5 * 1024 * 1024,     // 5MB
    maxRequestSize = 10 * 1024 * 1024  // 10MB
)
public class AddFoodController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Relative and absolute paths for image storage
    private static final String RELATIVE_IMAGE_DIR = "Resources" + File.separator + "Images" + File.separator + "System" + File.separator + "Cuisine";
    private static final String ABSOLUTE_IMAGE_DIR = "C:\\Users\\ARCHANA\\eclipse-workspace\\Savore\\src\\main\\webapp\\Resources\\Images\\System\\Cuisine";

    /**
     * Handles GET request — forwards to AddFood.jsp page
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Serving Add Food page...");
        request.getRequestDispatcher("/WEB-INF/pages/AddFood.jsp").forward(request, response);
    }

    /**
     * Handles POST request — processes the food item form and stores data & image
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Setup upload directories
            String appPath = request.getServletContext().getRealPath("");
            String relativeUploadPath = appPath + File.separator + RELATIVE_IMAGE_DIR;

            // Create directories if not exist
            new File(relativeUploadPath).mkdirs();
            new File(ABSOLUTE_IMAGE_DIR).mkdirs();
            System.out.println("Upload directories prepared.");

            // Collect form data
            String foodName = request.getParameter("foodName");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));
            String country = request.getParameter("country");
            String availability = request.getParameter("availability");

            System.out.println("Received form data: " + foodName + ", " + price + ", " + country);

            // Process uploaded image
            Part imagePart = request.getPart("imageFile");
            String originalName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
            String extension = originalName.substring(originalName.lastIndexOf("."));
            String fileName = "IMG_" + DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()) + extension;

            System.out.println("Image received: " + originalName + " | Saved as: " + fileName);

            // Save image to deployed directory
            try (InputStream input = imagePart.getInputStream();
                 OutputStream out = new FileOutputStream(new File(relativeUploadPath + File.separator + fileName))) {
                input.transferTo(out);
                System.out.println("Image saved to deployed folder.");
            }

            // Save image to development directory
            try (InputStream input = imagePart.getInputStream();
                 OutputStream out = new FileOutputStream(new File(ABSOLUTE_IMAGE_DIR + File.separator + fileName))) {
                input.transferTo(out);
                System.out.println("Image saved to development folder.");
            }

            // Save food item to database
            String imageUrl = fileName;
            FoodItems food = new FoodItems(foodName, description, price, country, imageUrl, availability);
            new FoodItemsDAO().insertFoodItem(food);
            System.out.println("Food item inserted into database: " + foodName);

            // Redirect to food list page
            response.sendRedirect(request.getContextPath() + "/FoodList");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Error occurred while adding food item.");
            request.setAttribute("error", "Failed to add food item.");
            request.getRequestDispatcher("/WEB-INF/pages/FoodList.jsp").forward(request, response);
        }
    }
}

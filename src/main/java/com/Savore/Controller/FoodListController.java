package com.Savore.Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.Savore.DAO.FoodItemsDAO;
import com.Savore.model.FoodItems;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 * Controller for listing, editing, and deleting food items.
 * 
 * Handles:
 * - GET: Display list of all food items
 * - POST: Edit or delete selected food item
 * 
 * author: 23048573_ArchanaGiri
 */
@WebServlet("/FoodList")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024) // 5MB max upload
public class FoodListController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private FoodItemsDAO foodItemsDao;

    private static final String IMAGE_FOLDER = "Resources/Images/System/Cuisine";
    private static final String ABSOLUTE_RESOURCE_PATH = "C:\\Users\\ARCHANA\\eclipse-workspace\\Savore\\src\\main\\webapp\\Resources\\Images\\System\\Cuisine";

    /**
     * Initializes DAO connection
     */
    @Override
    public void init() throws ServletException {
        try {
            foodItemsDao = new FoodItemsDAO();
            System.out.println("FoodItemsDAO initialized.");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Failed to initialize DAO.");
            throw new ServletException(e);
        }
    }

    /**
     * Handles GET request to fetch and display all food items
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<FoodItems> foodList = foodItemsDao.getAllFoodItems();
        request.setAttribute("foodList", foodList);
        System.out.println("Fetched " + foodList.size() + " food items.");
        request.getRequestDispatcher("/WEB-INF/pages/FoodList.jsp").forward(request, response);
    }

    /**
     * Handles POST requests: edit or delete food item
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            System.out.println("Edit action triggered.");
            editFood(request, response);
        } else if ("delete".equals(action)) {
            System.out.println("Delete action triggered.");
            deleteFood(request, response);
        }
    }

    /**
     * Handles food item editing with optional image update
     */
    private void editFood(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Collect form parameters
            int foodId = Integer.parseInt(request.getParameter("foodId"));
            String foodName = request.getParameter("foodName");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));
            String country = request.getParameter("country");
            String availability = request.getParameter("availability");
            String existingImageUrl = request.getParameter("existingImageUrl");

            // Handle uploaded image
            Part imagePart = request.getPart("imageFile");
            String imageUrl = existingImageUrl;

            if (imagePart != null && imagePart.getSize() > 0) {
                String originalFileName = imagePart.getSubmittedFileName();
                String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                String uniqueFileName = "IMG_" + DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
                        .format(LocalDateTime.now()) + extension;

                // Save to deployment folder
                String deploymentPath = request.getServletContext().getRealPath("") + File.separator + IMAGE_FOLDER;
                File deployDir = new File(deploymentPath);
                if (!deployDir.exists()) deployDir.mkdirs();
                try (InputStream input = imagePart.getInputStream();
                     OutputStream output = new FileOutputStream(new File(deploymentPath, uniqueFileName))) {
                    input.transferTo(output);
                }

                // Save to development folder
                File devDir = new File(ABSOLUTE_RESOURCE_PATH);
                if (!devDir.exists()) devDir.mkdirs();
                try (InputStream input = imagePart.getInputStream();
                     OutputStream output = new FileOutputStream(new File(devDir, uniqueFileName))) {
                    input.transferTo(output);
                }

                imageUrl = IMAGE_FOLDER + "/" + uniqueFileName;
                System.out.println("Image updated: " + imageUrl);
            }

            // Update food in database
            FoodItems updatedFood = new FoodItems(foodId, foodName, description, price, country, imageUrl, availability);
            foodItemsDao.updateFood(updatedFood);
            System.out.println("Food item updated: " + foodName);
            response.sendRedirect("FoodList");

        } catch (Exception e) {
            System.out.println("Error while editing food item.");
            e.printStackTrace();
            throw new ServletException("Failed to update food item", e);
        }
    }

    /**
     * Handles food item deletion by ID
     */
    private void deleteFood(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int foodId = Integer.parseInt(request.getParameter("foodId"));
            foodItemsDao.deleteFoodItem(foodId);
            System.out.println("Food item deleted. ID: " + foodId);
            response.sendRedirect("FoodList");
        } catch (Exception e) {
            System.out.println("❌ Error while deleting food item.");
            e.printStackTrace();
            throw new ServletException("Failed to delete food item", e);
        }
    }
}

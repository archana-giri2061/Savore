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

@WebServlet("/AddFood")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,   // 1MB
    maxFileSize = 5 * 1024 * 1024,     // 5MB
    maxRequestSize = 10 * 1024 * 1024  // 10MB
)
public class AddFoodController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String RELATIVE_IMAGE_DIR = "Resources" + File.separator + "Images" + File.separator + "System" + File.separator + "Cuisine";
    private static final String ABSOLUTE_IMAGE_DIR = "C:\\Users\\ARCHANA\\eclipse-workspace\\Savore\\src\\main\\webapp\\Resources\\Images\\System\\Cuisine";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/AddFood.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Prepare folder paths
            String appPath = request.getServletContext().getRealPath("");
            String relativeUploadPath = appPath + File.separator + RELATIVE_IMAGE_DIR;

            new File(relativeUploadPath).mkdirs();
            new File(ABSOLUTE_IMAGE_DIR).mkdirs();

            // Read form data
            String foodName = request.getParameter("foodName");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));
            String country = request.getParameter("country");
            String availability = request.getParameter("availability");

            // Handle image
            Part imagePart = request.getPart("imageFile");
            String originalName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
            String extension = originalName.substring(originalName.lastIndexOf("."));
            String fileName = "IMG_" + DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()) + extension;

            // Save to deployed folder
            try (InputStream input = imagePart.getInputStream();
                 OutputStream out = new FileOutputStream(new File(relativeUploadPath + File.separator + fileName))) {
                input.transferTo(out);
            }

            // Save to development folder
            try (InputStream input = imagePart.getInputStream();
                 OutputStream out = new FileOutputStream(new File(ABSOLUTE_IMAGE_DIR + File.separator + fileName))) {
                input.transferTo(out);
            }

            // Store only image name in DB
            String imageUrl = fileName;

            FoodItems food = new FoodItems(foodName, description, price, country, imageUrl, availability);
            new FoodItemsDAO().insertFoodItem(food);

            response.sendRedirect(request.getContextPath() + "/FoodList");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to add food item.");
            request.getRequestDispatcher("/WEB-INF/pages/FoodList.jsp").forward(request, response);
        }
    }
}

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

@WebServlet("/AddFood")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,   // 1MB
    maxFileSize = 5 * 1024 * 1024,     // 5MB
    maxRequestSize = 10 * 1024 * 1024  // 10MB
)
public class AddFoodController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String IMAGE_DIR = "Resources" + File.separator + "Images" + File.separator + "System" + File.separator + "Cuisine";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/AddFood.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get absolute path for storing images
        String appPath = request.getServletContext().getRealPath("");
        String uploadPath = appPath + File.separator + IMAGE_DIR;

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        try {
            String foodName = request.getParameter("foodName");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));
            String country = request.getParameter("country");
            String availability = request.getParameter("availability");

            // Get the uploaded image file name
            Part imagePart = request.getPart("imageFile");
            String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();

            // Save file on server (overwrite if exists)
            File fileToSave = new File(uploadPath + File.separator + fileName);
            try (InputStream input = imagePart.getInputStream();
                 OutputStream out = new FileOutputStream(fileToSave)) {
                input.transferTo(out);
            }

            // Save only the file name in the DB
            FoodItems food = new FoodItems(foodName, description, price, country, fileName, availability);
            FoodItemsDAO dao = new FoodItemsDAO();
            dao.insertFoodItem(food);

            // Redirect to food list page
            response.sendRedirect(request.getContextPath() + "/FoodList");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to add food item.");
            request.getRequestDispatcher("/WEB-INF/pages/FoodList.jsp").forward(request, response);
        }
    }
}

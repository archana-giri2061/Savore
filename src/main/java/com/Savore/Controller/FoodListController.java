package com.Savore.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
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

@WebServlet("/FoodList")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024)
public class FoodListController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private FoodItemsDAO foodItemsDao;

    @Override
    public void init() throws ServletException {
        try {
            foodItemsDao = new FoodItemsDAO();
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<FoodItems> foodList = foodItemsDao.getAllFoodItems();
        request.setAttribute("foodList", foodList);
        request.getRequestDispatcher("/WEB-INF/pages/FoodList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            editFood(request, response);
        } else if ("delete".equals(action)) {
            deleteFood(request, response);
        }
    }

    private void editFood(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int foodId = Integer.parseInt(request.getParameter("foodId"));
            String foodName = request.getParameter("foodName");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));
            String country = request.getParameter("country");
            String availability = request.getParameter("availability");
            String existingImageUrl = request.getParameter("existingImageUrl");

            Part imagePart = request.getPart("imageFile");
            String imageUrl = existingImageUrl;

            if (imagePart != null && imagePart.getSize() > 0) {
                String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
                String uploadPath = getServletContext().getRealPath("/") + "images";

                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();

                imageUrl = "images/" + fileName;
                imagePart.write(uploadPath + File.separator + fileName);
            }

            FoodItems updatedFood = new FoodItems(foodId, foodName, description, price, country, imageUrl, availability);
            foodItemsDao.updateFood(updatedFood);

            response.sendRedirect("FoodList");

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Failed to update food item", e);
        }
    }

    private void deleteFood(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int foodId = Integer.parseInt(request.getParameter("foodId"));
            foodItemsDao.deleteFoodItem(foodId);
            response.sendRedirect("FoodList");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Failed to delete food item", e);
        }
    }
}

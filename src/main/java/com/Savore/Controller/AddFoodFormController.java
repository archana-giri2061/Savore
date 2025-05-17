package com.Savore.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Controller to handle the routing to the Add Food form page.
 * Maps the "/AddFoodForm" URL to the JSP form view.
 * 
 * author: 23048573_ArchanaGiri
 */
@WebServlet("/AddFoodForm")
public class AddFoodFormController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Handles GET request to display the AddFood.jsp form page.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("Navigated to Add Food Form page.");
        
        // Forward the request to the AddFood.jsp page for user input
        request.getRequestDispatcher("/WEB-INF/pages/AddFood.jsp").forward(request, response);
    }
}

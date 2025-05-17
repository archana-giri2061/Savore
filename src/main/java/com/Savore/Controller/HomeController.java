package com.Savore.Controller;

import com.Savore.DAO.FoodItemsDAO;
import com.Savore.model.FoodItems;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Controller for the Home page.
 * Loads a list of featured cuisines (one per country) and forwards them to the Home.jsp page.
 * 
 * author: 23048573_ArchanaGiri
 */
@WebServlet(urlPatterns = {"/home"})
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles GET requests to the /home route.
	 * Retrieves one food item per country and forwards data to Home.jsp.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// Initialize DAO and fetch data
			FoodItemsDAO dao = new FoodItemsDAO();
			List<FoodItems> cuisineList = dao.getOneFoodPerCountry();

			// Set retrieved cuisine list to request scope
			req.setAttribute("cuisineList", cuisineList);
			System.out.println("HomeController: Loaded " + cuisineList.size() + " featured cuisines.");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("HomeController: Failed to load featured cuisines.");
			req.setAttribute("cuisineList", null);
		}

		// Forward to Home.jsp view
		req.getRequestDispatcher("/WEB-INF/pages/Home.jsp").forward(req, resp);
	}
}

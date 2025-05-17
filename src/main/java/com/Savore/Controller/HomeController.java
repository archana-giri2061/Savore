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

@WebServlet(urlPatterns = {"/home"})
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			FoodItemsDAO dao = new FoodItemsDAO();
			List<FoodItems> cuisineList = dao.getOneFoodPerCountry();
			req.setAttribute("cuisineList", cuisineList);
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("cuisineList", null);
		}

		req.getRequestDispatcher("/WEB-INF/pages/Home.jsp").forward(req, resp);
	}
}

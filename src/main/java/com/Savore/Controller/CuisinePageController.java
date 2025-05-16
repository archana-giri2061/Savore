package com.Savore.Controller;

import com.Savore.DAO.FoodItemsDAO;
import com.Savore.model.FoodItems;
import com.Savore.util.CookieUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/CuisinePage")
public class CuisinePageController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String userName = null;

        // 🟩 Handle session or cookie-based login
        if (session != null && session.getAttribute("userName") != null) {
            userName = (String) session.getAttribute("userName");
        } else {
            Cookie usernameCookie = CookieUtil.getCookie(request, "userName");
            if (usernameCookie != null) {
                userName = usernameCookie.getValue();
                session = request.getSession();
                session.setAttribute("userName", userName);
            }
        }

        if (userName == null) {
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }

        // 🟩 Load food items: all or filtered by country
        FoodItemsDAO dao = null;
        List<FoodItems> foodList = null;

        try {
            dao = new FoodItemsDAO();
            String country = request.getParameter("country");

            if (country != null && !country.trim().isEmpty()) {
                foodList = dao.getFoodByCountry(country.trim());
            } else {
                foodList = dao.getAllFoodItems();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("foodList", foodList);
        request.setAttribute("username", userName);
        request.getRequestDispatcher("/WEB-INF/pages/CuisinePage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

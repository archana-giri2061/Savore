package com.Savore.Controller;

import com.Savore.DAO.UserDAO;
import com.Savore.model.UserModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/AdminProfile")
public class AdminProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
        	request.getRequestDispatcher("/WEB-INF/pages/AdminProfile.jsp").forward(request, response);
            return;
        }

        String email = (String) session.getAttribute("userEmail");
        UserDAO dao = new UserDAO();

        UserModel currentUser = dao.getUserByEmail(email);
        List<UserModel> allAdmins = dao.getAllAdmins();

        request.setAttribute("adminUser", currentUser);
        request.setAttribute("adminList", allAdmins);
        request.getRequestDispatcher("/WEB-INF/pages/AdminProfile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
        	response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        String email = (String) session.getAttribute("userEmail");
        String newPassword = request.getParameter("newPassword");

        UserDAO dao = new UserDAO();
        dao.updatePassword(email, newPassword); // Make sure this method exists in UserDAO

        response.sendRedirect("AdminProfile");
    }
}

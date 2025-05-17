package com.Savore.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller to serve the Contact Us page for all users (public access).
 * This servlet is mapped to the URL pattern "/ContactUs".
 * It allows both GET and POST requests to display the ContactUs.jsp page.
 * 
 * @author 23048573_ArchanaGiri
 */
@WebServlet("/ContactUs")
public class ContactUsController extends HttpServlet {

    // Serialization UID for maintaining servlet version compatibility
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests for the Contact Us page.
     * This method simply forwards the request to the JSP view,
     * allowing both guests and authenticated users to access the page.
     *
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Forward the request to the JSP located in WEB-INF/pages directory
        request.getRequestDispatcher("/WEB-INF/pages/ContactUs.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for the Contact Us page.
     * Delegates to doGet() to display the same content.
     *
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Delegate POST handling to GET method for consistent behavior
        doGet(request, response);
    }
}

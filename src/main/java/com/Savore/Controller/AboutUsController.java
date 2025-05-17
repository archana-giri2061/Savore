package com.Savore.Controller;

import com.Savore.util.CookieUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet for handling About Us page access for Savoré.
 * This controller ensures that only authenticated users can access the About Us page.
 * It checks for valid sessions or cookies to verify authentication and handles redirection appropriately.
 * 
 * author: 23048573_ArchanaGiri
 */
@WebServlet("/AboutUs")
public class AboutUsController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests to /AboutUs
     * Performs authentication checks using session and cookies.
     * If authenticated, forwards request to the AboutUs.jsp page.
     * If not, redirects to the login page.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // STEP 1: Attempt to retrieve existing session without creating a new one
        HttpSession session = request.getSession(false);
        String userName = null;

        // STEP 2: Check if session contains an authenticated user
        if (session != null && session.getAttribute("userName") != null) {
            userName = (String) session.getAttribute("userName");
            System.out.println("User authenticated via session: " + userName);
        } else {
            // STEP 3: If no session exists, try retrieving the username from a cookie
            Cookie usernameCookie = CookieUtil.getCookie(request, "userName");
            if (usernameCookie != null) {
                userName = usernameCookie.getValue();

                // STEP 4: If cookie exists, create a new session and restore the username
                session = request.getSession(); // create a new session
                session.setAttribute("userName", userName);
                System.out.println("Session restored from cookie: " + userName);
            }
        }

        // STEP 5: If authentication fails (no session or cookie), redirect to login page
        if (userName == null) {
            System.out.println("Unauthorized access to /AboutUs. Redirecting to login.");
            response.sendRedirect(request.getContextPath() + "/AboutUs");
            return;
        }

        // STEP 6: If authenticated, set attribute and forward to AboutUs.jsp
        request.setAttribute("userName", userName); // Optional: used to personalize About Us page
        request.getRequestDispatcher("/WEB-INF/pages/AboutUs.jsp").forward(request, response);
    }

    /**
     * Handles POST requests to /AboutUs
     * Delegates the request to doGet for same handling logic.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Reuse the same logic for POST requests
        doGet(request, response);
    }
}

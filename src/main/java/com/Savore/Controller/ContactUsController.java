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
 * Servlet implementation for the Contact Us page.
 * Handles requests for displaying the 'Contact Us' page for Savoré.
 */
@WebServlet("/ContactUs")
public class ContactUsController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests to /ContactUs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 🔐 STEP 1: Try retrieving existing session (without creating new one)
        HttpSession session = request.getSession(false);
        String userName = null;

        // ✅ STEP 2: If session exists, check if user is logged in
        if (session != null && session.getAttribute("userName") != null) {
            userName = (String) session.getAttribute("userName");
            System.out.println("✅ User authenticated via session: " + userName);
        } else {
            // 🟨 STEP 3: No session found, so check for 'username' cookie
            Cookie userNameCookie = CookieUtil.getCookie(request, "userName");
            if (userNameCookie != null) {
                userName = userNameCookie.getValue();

                // ♻️ STEP 4: If cookie is found, restore the session
                session = request.getSession(); // creates a new session
                session.setAttribute("username", userName);
                System.out.println("🔁 Session restored from cookie: " + userName);
            }
        }

        // ❌ STEP 5: If username still null, user isn't logged in
        if (userName == null) {
            System.out.println("⛔ Unauthorized access to /ContactUs. Redirecting to login.");
            response.sendRedirect(request.getContextPath() + "/ContactUs");
            return;
        }

        // ✅ STEP 6: User is authenticated. Proceed to contact page
        request.setAttribute("username", userName); // optional: for personalization
        request.getRequestDispatcher("/WEB-INF/pages/ContactUs.jsp").forward(request, response);
    }

    /**
     * Handles POST requests to /ContactUs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Use GET logic for POST as well (common in display pages)
        doGet(request, response);
    }
}
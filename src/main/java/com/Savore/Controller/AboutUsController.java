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
 * Includes session and cookie validation to ensure user authentication.
 */
@WebServlet("/AboutUs")
public class AboutUsController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests to /AboutUs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 🔐 STEP 1: Attempt to retrieve existing session without creating a new one
        HttpSession session = request.getSession(false);
        String userName = null;

        // ✅ STEP 2: Check if user is authenticated via session
        if (session != null && session.getAttribute("userName") != null) {
            userName = (String) session.getAttribute("userName");
            System.out.println("✅ User authenticated via session: " + userName);
        } else {
            // 🟨 STEP 3: If no session, check cookie for saved username
            Cookie usernameCookie = CookieUtil.getCookie(request, "userName");
            if (usernameCookie != null) {
                userName = usernameCookie.getValue();

                // ♻️ STEP 4: Recreate session from cookie if necessary
                session = request.getSession(); // create a new session
                session.setAttribute("userName", userName);
                System.out.println("🔁 Session restored from cookie: " + userName);
            }
        }

        // ❌ STEP 5: If user not authenticated, redirect to login page
        if (userName == null) {
            System.out.println("⛔ Unauthorized access to /AboutUs. Redirecting to login.");
            response.sendRedirect(request.getContextPath() + "/AboutUs");
            return;
        }

        // ✅ STEP 6: Authenticated — pass data to the JSP if needed
        request.setAttribute("userName", userName); // optional for personalization
        request.getRequestDispatcher("/WEB-INF/pages/AboutUs.jsp").forward(request, response);
    }

    /**
     * Handles POST requests to /AboutUs (same as GET)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Reuse the same logic for POST
        doGet(request, response);
    }
}
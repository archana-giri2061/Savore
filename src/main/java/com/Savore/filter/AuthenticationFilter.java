package com.Savore.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Servlet Filter to enforce authentication checks on protected routes.
 * Ensures users cannot access sensitive URLs without logging in.
 * 
 * author: 23048573_ArchanaGiri
 */
@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    /**
     * List of publicly accessible endpoints and resource paths.
     */
    private static final List<String> PUBLIC_URLS = Arrays.asList(
        "/home", "/Login", "/logIn", "/logIn.jsp", "/Register", "/RegistrationPage", "/ContactUs", "/AboutUs", "/LandingPage", "/orderForm", "/SearchController", "/Logout",
        "/CSS/", "/JS/", "/images/", "/Resources/", "/fonts/", "/favicon.ico"
    );

    /**
     * List of admin-only or protected pages.
     */
    private static final List<String> PROTECTED_URLS = Arrays.asList(
        "/AdminDashboard", "/AdminProfile", "/FoodList", "/AddFood", "/AddFoodForm"
    );

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("AuthenticationFilter initialized.");
    }

    /**
     * Filters every request and checks if access should be allowed based on session state.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getServletPath();
        HttpSession session = req.getSession(false);

        boolean isPublic = PUBLIC_URLS.stream().anyMatch(path::startsWith);
        boolean isProtected = PROTECTED_URLS.stream().anyMatch(path::startsWith);
        boolean isLoggedIn = (session != null && session.getAttribute("loggedInUser") != null);

        if (isProtected && !isLoggedIn) {
            System.out.println("Unauthorized access to: " + path + ". Redirecting to login.");
            res.sendRedirect(req.getContextPath() + "/logIn");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        System.out.println("AuthenticationFilter destroyed.");
    }
}

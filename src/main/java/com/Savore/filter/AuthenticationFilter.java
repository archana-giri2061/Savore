package com.Savore.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    private static final List<String> PUBLIC_URLS = Arrays.asList(
        "/home", "/Login", "/logIn.jsp", "/Register", "/ContactUs", "/aboutus",
        "/CSS/", "/JS/", "/images/", "/Resources/", "/fonts/", "/favicon.ico"
    );

    private static final List<String> PROTECTED_URLS = Arrays.asList(
        "/AdminDashboard", "/AdminProfile", "/FoodList", "/AddFood", "/AddFoodForm"
    );

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getServletPath();
        HttpSession session = req.getSession(false);

        // Check if the request is for a public URL or static content
        boolean isPublic = PUBLIC_URLS.stream().anyMatch(path::startsWith);
        boolean isProtected = PROTECTED_URLS.stream().anyMatch(path::startsWith);

        boolean isLoggedIn = (session != null && session.getAttribute("loggedInUser") != null);

        if (isProtected && !isLoggedIn) {
            System.out.println("⛔ Unauthorized access to " + path + ". Redirecting to login.");
            res.sendRedirect(req.getContextPath() + "/logIn.jsp");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {}
}

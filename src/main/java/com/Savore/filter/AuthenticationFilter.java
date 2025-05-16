//package com.Savore.filter;
//
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import java.io.IOException;
//
//@WebFilter(urlPatterns = "/*")
//public class AuthenticationFilter implements Filter {
//
//    private static final String[] PUBLIC_URIS = {
//        "/logIn", "/logIn.jsp",
//        "/RegistrationPage", "/register.jsp",
//        "/Home", "/index.jsp", "/"
//    };
//
//    private static final String[] ADMIN_URIS = {
//        "/AdminDashboard", "/AddFood", "/FoodList", "/AdminProfile"
//    };
//
//    private static final String[] USER_URIS = {
//        "/OrderNow", "/CuisinePage", "/ContactUs", "/AboutUs"
//    };
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        // No initialization needed
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//        throws IOException, ServletException {
//
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse res = (HttpServletResponse) response;
//        HttpSession session = req.getSession(false);
//
//        String uri = req.getRequestURI();
//        String contextPath = req.getContextPath();
//        String path = uri.substring(contextPath.length());
//
//        // Allow static resources
//        if (path.matches(".*(\\.css|\\.js|\\.png|\\.jpg|\\.jpeg|\\.gif|\\.woff|\\.ttf|\\.svg)$")) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        // Allow public URIs
//        for (String publicUri : PUBLIC_URIS) {
//            if (path.equals(publicUri)) {
//                chain.doFilter(request, response);
//                return;
//            }
//        }
//
//        // Check session
//        boolean isLoggedIn = session != null && session.getAttribute("user") != null;
//        String role = (session != null) ? (String) session.getAttribute("role") : null;
//
//        if (!isLoggedIn) {
//            res.sendRedirect(contextPath + "/logIn.jsp");
//            return;
//        }
//
//        // Admin role control
//        if ("ADMIN".equalsIgnoreCase(role)) {
//            for (String adminUri : ADMIN_URIS) {
//                if (path.equals(adminUri)) {
//                    chain.doFilter(request, response);
//                    return;
//                }
//            }
//            res.sendRedirect(contextPath + "/AdminDashboard");
//            return;
//        }
//
//        // User role control
//        if ("USER".equalsIgnoreCase(role)) {
//            for (String userUri : USER_URIS) {
//                if (path.equals(userUri)) {
//                    chain.doFilter(request, response);
//                    return;
//                }
//            }
//            res.sendRedirect(contextPath + "/Home");
//            return;
//        }
//
//        // Fallback: redirect to login
//        res.sendRedirect(contextPath + "/logIn.jsp");
//    }
//
//    @Override
//    public void destroy() {
//        // Cleanup if needed
//    }
//}

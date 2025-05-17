package com.Savore.util;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Utility class for handling request redirection and setting messages in Savoré.
 * Provides central navigation support for controllers and UI feedback handling.
 * 
 * author: Archana Giri (23048573)
 */
public class RedirectionUtil {

    // Base path for JSP pages inside WEB-INF
    private static final String baseUrl = "/WEB-INF/pages/";

    /** Path to Registration Page */
    public static final String registerUrl = baseUrl + "RegistrationPage.jsp";

    /** Path to Login Page (Forwarded to a servlet mapped at /logIn) */
    public static final String loginUrl = baseUrl + "LogIn";

    /** Path to Home Page */
    public static final String homeUrl = baseUrl + "Home.jsp";

    /**
     * Sets a message attribute (e.g., successMessage or errorMessage) on the request.
     *
     * @param req     the HttpServletRequest to set the attribute on
     * @param msgType the key under which the message is stored (e.g., "successMessage")
     * @param msg     the message text to display
     */
    public void setMsgAttribute(HttpServletRequest req, String msgType, String msg) {
        req.setAttribute(msgType, msg);
    }

    /**
     * Forwards the request and response to the specified JSP page.
     *
     * @param req  the request object
     * @param resp the response object
     * @param page the full path to the target JSP page
     * @throws ServletException if the forwarding fails
     * @throws IOException      if an I/O error occurs
     */
    public void redirectToPage(HttpServletRequest req, HttpServletResponse resp, String page)
            throws ServletException, IOException {
        req.getRequestDispatcher(page).forward(req, resp);
    }

    /**
     * Sets a message and redirects to a specified page.
     * Combines {@code setMsgAttribute()} and {@code redirectToPage()}.
     *
     * @param req     the request object
     * @param resp    the response object
     * @param msgType message attribute key (e.g., "errorMessage")
     * @param msg     the message to set
     * @param page    the full path to the JSP page
     * @throws ServletException if dispatching fails
     * @throws IOException      if an I/O error occurs
     */
    public void setMsgAndRedirect(HttpServletRequest req, HttpServletResponse resp, String msgType, String msg,
                                  String page) throws ServletException, IOException {
        setMsgAttribute(req, msgType, msg);
        redirectToPage(req, resp, page);
    }
}

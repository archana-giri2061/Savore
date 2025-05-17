package com.Savore.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Utility class for managing HTTP session attributes in a web application.
 * Provides methods to set, retrieve, remove, and invalidate session data.
 * 
 * author: 23048573_ArchanaGiri
 */
public class SessionUtil {

    /**
     * Sets an attribute in the session. If a session does not already exist, it will be created.
     *
     * @param request the HttpServletRequest from which to obtain or create the session
     * @param key     the name under which the value will be stored
     * @param value   the object to be stored in the session
     */
    public static void setAttribute(HttpServletRequest request, String key, Object value) {
        HttpSession session = request.getSession();
        session.setAttribute(key, value);
    }

    /**
     * Retrieves an attribute from the session without creating a new one.
     *
     * @param request the HttpServletRequest from which to obtain the session
     * @param key     the name of the attribute to retrieve
     * @return the attribute value, or null if the session does not exist or the attribute is not found
     */
    public static Object getAttribute(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return session.getAttribute(key);
        }
        return null;
    }

    /**
     * Removes an attribute from the session if the session exists.
     *
     * @param request the HttpServletRequest from which to obtain the session
     * @param key     the name of the attribute to remove
     */
    public static void removeAttribute(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(key);
        }
    }

    /**
     * Invalidates the current session, effectively logging the user out.
     *
     * @param request the HttpServletRequest from which to obtain the session
     */
    public static void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}

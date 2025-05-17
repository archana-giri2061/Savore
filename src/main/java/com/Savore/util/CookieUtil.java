package com.Savore.util;

import java.util.Arrays;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Utility class to manage cookies for the Savoré website.
 * Provides methods to add, retrieve, and delete cookies across the application.
 * 
 * author: 23048573_ArchanaGiri
 */
public class CookieUtil {

    /**
     * Adds a cookie to the user's browser.
     *
     * @param res    the HttpServletResponse to add the cookie to
     * @param name   the name of the cookie
     * @param value  the value to be stored in the cookie
     * @param maxAge the maximum age (in seconds) before the cookie expires
     */
    public static void addCookie(HttpServletResponse res, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/"); // Makes it available throughout the site
        res.addCookie(cookie);
        System.out.println("Cookie added: " + name + "=" + value + ", Max-Age: " + maxAge);
    }

    /**
     * Retrieves a cookie from the request by its name.
     *
     * @param req  the HttpServletRequest containing the cookies
     * @param name the name of the cookie to retrieve
     * @return the Cookie object if found, otherwise null
     */
    public static Cookie getCookie(HttpServletRequest req, String name) {
        if (req.getCookies() != null) {
            return Arrays.stream(req.getCookies())
                    .filter(cookie -> name.equals(cookie.getName()))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    /**
     * Deletes a cookie from the user's browser by setting its max age to 0.
     *
     * @param res  the HttpServletResponse to send the expired cookie
     * @param name the name of the cookie to delete
     */
    public static void deleteCookie(HttpServletResponse res, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0); // Instruct browser to delete cookie
        cookie.setPath("/"); // Ensure the same path as the original cookie
        res.addCookie(cookie);
        System.out.println("Cookie deleted: " + name);
    }
}

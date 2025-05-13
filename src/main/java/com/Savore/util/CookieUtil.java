package com.Savore.util;

import java.util.Arrays;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Utility class to manage cookies for this website
 * provides methods to add, retrieve, and delete cookies
 */
public class CookieUtil {

    public static void addCookie(HttpServletResponse res, String userName, String value, int maxAge) {
        Cookie cookie = new Cookie(userName, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        res.addCookie(cookie);
    }

    public static Cookie getCookie(HttpServletRequest req, String name) {
        if (req.getCookies() != null) {
            return Arrays.stream(req.getCookies())
                    .filter(cookie -> name.equals(cookie.getName()))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    public static void deleteCookie(HttpServletResponse res, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/"); // Making the cookie universal throughout the site
        res.addCookie(cookie);
    }
}
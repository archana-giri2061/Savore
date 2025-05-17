<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Footer</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/Footer.css">
</head>
<body>
<footer class="footer-section">
    <div class="footer-main">
        <!-- Logo and Tagline -->
        <div class="footer-brand">
            <img src="${pageContext.request.contextPath}/Resources/Images/System/FinalLogo.png" alt="Savore Logo" />
            <p class="tagline">A World of Flavors, Crafted by Experts</p>

            <div class="email-cta">
                <input type="email" placeholder="Enter your email">
                <button onclick="window.location.href='${pageContext.request.contextPath}/logIn.jsp'" class="cta-button">Login</button>
            </div>
        </div>

        <!-- Quick Links -->
        <div class="footer-links">
            <h4>Quick Links</h4>
            <a href="${pageContext.request.contextPath}/AboutUs">About Us</a>
            <a href="${pageContext.request.contextPath}/ContactUs">Contact Us</a>
        </div>

        <!-- Social Icons -->
        <div class="footer-social">
            <h4>Join with</h4>
            <div class="icons">
                <img src="${pageContext.request.contextPath}/Resources/Images/System/facebook.png" alt="Facebook" />
                <img src="${pageContext.request.contextPath}/Resources/Images/System/gmail.png" alt="Gmail" />
                <img src="${pageContext.request.contextPath}/Resources/Images/System/instagram.png" alt="Instagram" />
            </div>
        </div>
    </div>

    <p class="copyright">© 2025 Savoré. All rights reserved.</p>
</footer>
</body>
</html>

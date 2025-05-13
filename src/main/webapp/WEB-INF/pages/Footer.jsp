<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/Footer.css">
</head>
<body>
	<footer class="footer-section">
        <div class="footer-content">
            <div class="logo-tagline">
                <div class="logo">
                    <img src="${pageContext.request.contextPath}/Resources/Images/System/FinalLogo.png" alt="logo">
                </div>
            </div>
            <p class="tagline">A World of Flavors, Crafted by Experts</p>
        </div>
        <div class="email-cta">
            <input type="email" placeholder="Enter your email">
            <button href="${pageContext.request.contextPath}/logIn.jsp" class="try-button">Login</button>
        </div>
        <div class="footer-links-container">
            <nav class="footer-links">
                <p class="links">Quick Links</p>
                <a href="#">About Us</a>
                <a href="#">Contact Us</a>
            </nav>
            <div class="social-icons">
                <p>Join with</p>
                <div class="icons">
                    <img src="${pageContext.request.contextPath}/Resources/Images/System/facebook.png" alt="Facebook" />
                    <img src="${pageContext.request.contextPath}/Resources/Images/System/gmail.png" alt="Gmail" />
                    <img src="${pageContext.request.contextPath}/Resources/Images/System/instagram.png" alt="Instagram" />
                </div>
            </div>
        </div>
        <p>@ 2025 Savore</p>
    </footer>
</body>
</html>
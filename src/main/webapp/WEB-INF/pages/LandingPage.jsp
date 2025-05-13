<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Savore</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/LandingPage.css">
</head>
<body>
    <nav class="navbar">
        <div class="logo"><img src="${pageContext.request.contextPath}/Resources/Images/System/FinalLogo.png" alt="Italian Trulli"></div>
        <a href="${pageContext.request.contextPath}/logIn" class="btn-link">Login</a>
    </nav>
    <div class="hero">
        <div class="hero-content">
            <h1 class="hero-title">Savor the World at Your Table</h1>
            <p class="hero-subtitle">
                we bring the worlds most delicious cuisines right to your fingertips. Whether you are craving spicy Indian curries, creamy Italian pastas, fresh Japanese sushi, or flavorful Mexican tacos, we’ve got it all in one place.
                Our platform features dishes from all over the world, prepared with love by the best and most experienced chefs who are masters of their culinary traditions. Each recipe is crafted with authentic ingredients to ensure every bite gives you a true taste of the culture it comes from.
                We believe food connects people—and with Savore, you can explore new cultures, flavors, and experiences without leaving your home
            </p>
            <button class="hero-button">About Us</button>
        </div>
        <img class="hero-image" src="${pageContext.request.contextPath}/Resources/Images/System/chef.jpg" alt="hero">
    </div>

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
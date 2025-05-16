<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Header</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/Header.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display&display=swap" rel="stylesheet">
</head>
<body>
    <header>
        <div class="logo">
            <a href="${pageContext.request.contextPath}/Home">
                <img src="${pageContext.request.contextPath}/Resources/Images/System/FinalLogo.png" alt="logo">
            </a>
        </div>

        <nav>
            <ul>
                <li><a href="${pageContext.request.contextPath}/Home">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/CuisinePage">Cuisine</a></li>
                <li><a href="${pageContext.request.contextPath}/ContactUs">Contact Us</a></li>
                <li><a href="${pageContext.request.contextPath}/AboutUs">About Us</a></li>
            </ul>
        </nav>

        <div class="profile-icon">
            <a href="${pageContext.request.contextPath}/UserProfile" title="User Profile">
                <i class="fas fa-user"></i>
            </a>
        </div>
    </header>
</body>
</html>

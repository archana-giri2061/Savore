<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Header</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/Header.css">
</head>
<body>
    <header>
        <div class="logo">
            <img src="${pageContext.request.contextPath}/Resources/Images/System/FinalLogo.png" alt="logo">
        </div>
        <nav>
            <ul>
                <li><a href="${pageContext.request.contextPath}/Home">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/CuisinePage">Cuisine</a></li>
                <li><a href="${pageContext.request.contextPath}/ContactUs">Contact Us</a></li>
                <li><a href="${pageContext.request.contextPath}/AboutUs">About Us</a></li>

                <!-- ✅ Show welcome only if logged in as USER -->
                <div class="profile-icon">
                	<a href="${pageContext.request.contextPath}/UserProfile">
                    <i class="fas fa-user"></i>
                </a>
            </div>
            </ul>
        </nav>
    </header>
</body>
</html>

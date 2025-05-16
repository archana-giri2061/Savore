<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register - Savoré</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/RegistrationPage.css">
</head>
<body>
    <nav class="navbar">
        <div class="logo">
            <img src="${pageContext.request.contextPath}/Resources/Images/System/FinalLogo.png" alt="Savoré Logo">
        </div>
    </nav>
    <div class="container">
        <div class="form-box">
            <h1><span style="color: #b45f06;">Savoré</span></h1>
            <h2>Create Account</h2>

            <c:if test="${not empty error}">
                <div class="error-message">${error}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/RegistrationPage" method="post" enctype="multipart/form-data">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

                <label for="fullname">Full Name</label>
                <input type="text" id="fullname" name="fullname" placeholder="Enter full name" required maxlength="50">

                <label for="email">Email Address</label>
                <input type="email" id="email" name="email" placeholder="Enter email" required maxlength="100">

                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="Enter password" required>

                <label for="address">Address</label>
                <input type="text" id="address" name="address" placeholder="Enter address">
                
                <label for="profileImage">Profile Image</label>
                <input type="file" id="profileImage" name="profileImage" accept="image/*" required>

                <label for="role">Role</label>
                <select id="role" name="role" required>
                    <option value="">Select role</option>
                    <option value="ADMIN">Admin</option>
                    <option value="USER">User</option>
                </select>

                <button type="submit" class="signup-btn">Sign Up</button>
            </form>

            <p>Already have an account? <a href="${pageContext.request.contextPath}/logIn">Log In</a></p>
            <p>Join with</p>
            <div class="social-icons">
                <img src="${pageContext.request.contextPath}/Resources/Images/System/facebook-icon.png" alt="Facebook">
                <img src="${pageContext.request.contextPath}/Resources/Images/System/mail-icon.png" alt="Email">
                <img src="${pageContext.request.contextPath}/Resources/Images/System/camera-icon.png" alt="Other">
            </div>
        </div>
    </div>
</body>
</html>
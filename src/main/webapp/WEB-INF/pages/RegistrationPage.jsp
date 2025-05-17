<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register - Savoré</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/RegistrationPage.css">
</head>
<body>

<div class="wrapper">
    <!-- Left Image -->
    <div class="image-side">
        <img src="${pageContext.request.contextPath}/Resources/Images/System/Home/RegisterBanner.jpg" alt="Register Banner">
    </div>

    <!-- Right Form -->
    <div class="form-side">
        <div class="form-card">
            <img src="${pageContext.request.contextPath}/Resources/Images/System/FinalLogo.png" class="logo" alt="Savoré Logo">
            <h2>Create Account</h2>
            <p>Sign up with your personal information</p>

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

                <button type="submit" class="submit-btn">Sign Up</button>
            </form>

            <p class="login-text">Already have an account? <a href="${pageContext.request.contextPath}/logIn" class="login-link">Log In</a></p>
        </div>
    </div>
</div>

</body>
</html>

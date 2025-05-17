<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Savoré</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/LogIn.css"/>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
</head>
<body>
    <div class="login-wrapper">
        <div class="login-box">
            <div class="login-left">
                <img src="${pageContext.request.contextPath}/Resources/Images/System/Home/loginBanner.jpg" alt="Visual" />
            </div>

            <div class="login-right">
                <div class="login-form">
                    <img src="${pageContext.request.contextPath}/Resources/Images/System/FinalLogo.png" alt="Savoré Logo" style=" height: 50px; padding-left: 90px;">
                    <h2>Welcome Back</h2>
                    <p>Sign in with your username or email and password</p>

                    <c:if test="${not empty error}">
                        <div class="error-message">${error}</div>
                    </c:if>

                    <c:if test="${not empty successMessage}">
                        <div class="success-message">${successMessage}</div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/logIn" method="POST">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

                        <label for="identifier">Username</label>
                        <input type="text" id="identifier" name="identifier" placeholder="Enter username or email" required>

                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" placeholder="Enter password" required>

                        <div class="remember-row">
                            <input type="checkbox" id="rememberMe" name="rememberMe">
                            <label for="rememberMe">Remember Me</label>
                        </div>

                        <button type="submit" class="login-button">Log In</button>
                    </form>

                    <a href="${pageContext.request.contextPath}/forgotpassword" class="forgot-link">Forgot Password?</a>
                    <a href="${pageContext.request.contextPath}/RegistrationPage" class="register-link">Create new account</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>

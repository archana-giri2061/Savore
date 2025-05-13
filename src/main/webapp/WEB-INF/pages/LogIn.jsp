<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Savoré</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/LogIn.css"/>
</head>
<body>
    <nav class="navbar">
        <div class="logo">
            <img src="${pageContext.request.contextPath}/Resources/Images/System/FinalLogo.png" alt="Savoré Logo">
        </div>
    </nav>
    <div class="container">
        <div class="login-card">
            <h1 class="brand"><span style="color: #b45f06;">Savoré</span></h1>
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

                <div class="row">
                    <div class="col">
                        <label for="identifier">Username</label>
                        <input type="text" id="identifier" value="Archana Giri" name="identifier" placeholder="Enter username or email" required aria-label="Username or Email">
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <label for="password">Password:</label>
                        <input type="password" id="password" value="archana123" name="password" placeholder="Enter password" required aria-label="Password">
                    </div>
                </div>
                <div class="row checkbox-row">
                    <div class="col">
                        <input type="checkbox" id="rememberMe" name="rememberMe">
                        <label for="rememberMe" class="checkbox-label">Remember Me</label>
                    </div>
                </div>
                <div class="row">
                    <button type="submit" class="login-button">Log In</button>
                </div>
            </form>

            <div class="row">
                <a href="${pageContext.request.contextPath}/forgotpassword" class="forgot-button">Forgot Password?</a>
            </div>
            <div class="row">
                <a href="${pageContext.request.contextPath}/RegistrationPage" class="register-button">Create new account</a>
            </div>
        </div>
    </div>
</body>
</html>
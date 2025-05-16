<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/UserProfile.css">
</head>
<body>
<div class="container">
    <header>
        <div class="logo">
            <img src="${pageContext.request.contextPath}/Resources/Images/System/FinalLogo.png" alt="logo">
        </div>
        <nav>
            <span class="mr-4 text-gray-600">Welcome, ${sessionScope.loggedInUser.username}</span>
        </nav>
    </header>

    <h1>User Profile</h1>
    <c:if test="${not empty successMessage}">
        <p class="message" style="color: green;">${successMessage}</p>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <p class="message" style="color: red;">${errorMessage}</p>
    </c:if>

    <div style="display: flex; justify-content: space-between; align-items: flex-start; gap: 40px;">
        <div style="flex: 1;">
            <!-- Update Profile Form -->
            <form action="${pageContext.request.contextPath}/profile" method="post" enctype="multipart/form-data">
                <input type="hidden" name="action" value="update">
                <label for="firstName">Username:</label>
                <input type="text" id="firstName" name="firstName" value="${user.user_Name}" required>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${user.user_Email}" required>

                <label for="address">Address:</label>
                <input type="text" id="address" name="address" value="${user.user_Address}">

                <label for="profileImage">Update Profile Image:</label>
                <input type="file" id="profileImage" name="profileImage" accept="image/*">

                <button type="submit">Update Profile</button>
            </form>

            <!-- Change Password Form -->
            <form action="${pageContext.request.contextPath}/profile" method="post" style="margin-top: 20px;">
                <input type="hidden" name="action" value="changePassword">
                <label for="password">New Password:</label>
                <input type="password" id="password" name="password" required>
                <button type="submit">Change Password</button>
            </form>
        </div>

        <div style="text-align: center;">
            <img 
                src="${pageContext.request.contextPath}/Resources/Images/user/${user.image_path}" 
                alt="Profile Image"
                onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/Resources/Images/user/default_avatar.png';"
                style="width: 150px; height: 150px; object-fit: cover; border-radius: 50%; border: 3px solid #ccc;">
            <p style="margin-top: 10px; font-weight: bold;">${user.user_Name}</p>
        </div>
    </div>
</div>
</body>
</html>

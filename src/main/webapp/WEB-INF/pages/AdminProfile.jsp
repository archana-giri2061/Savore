
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/AdminProfile.css">
</head>
<body>
<div class="container">
    <header>
        <div class="logo">
            <img src="${pageContext.request.contextPath}/Resources/Images/System/FinalLogo.png" alt="logo">
        </div>
        <nav>
            <span class="mr-4 text-gray-600">Welcome, ${sessionScope.admin.username}</span>
        </nav>
    </header>

    <h1>Admin Profile</h1>
    <c:if test="${not empty message}"><p class="message">${message}</p></c:if>

    <div style="display: flex; justify-content: space-between; align-items: flex-start; gap: 40px;">
        <div style="flex: 1;">
            <form action="${pageContext.request.contextPath}/AdminProfile" method="post" enctype="multipart/form-data">
                <input type="hidden" name="action" value="update">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" value="${admin.username}" required>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${admin.email}" required>

                <label for="address">Address:</label>
                <input type="text" id="address" name="address" value="${admin.address}">

                <label for="profileImage">Update Profile Image:</label>
                <input type="file" id="profileImage" name="profileImage" accept="image/*">

                <button type="submit">Update Profile</button>
            </form>

            <form action="${pageContext.request.contextPath}/AdminProfile" method="post" style="margin-top: 20px;">
                <input type="hidden" name="action" value="changePassword">
                <label for="password">New Password:</label>
                <input type="password" id="password" name="password" required>
                <button type="submit">Change Password</button>
            </form>
        </div>

        <div style="text-align: center;">
            <img 
                src="${pageContext.request.contextPath}/${empty admin.image_URL ? 'Resources/Images/System/Profile/DummyImage.png' : admin.image_URL}" 
                alt="Profile Image"
                style="width: 150px; height: 150px; object-fit: cover; border-radius: 50%; border: 3px solid #ccc;">
            <p style="margin-top: 10px; font-weight: bold;">${admin.username}</p>
        </div>
    </div>

    <h2 style="margin-top: 40px;">All Registered Users</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Email</th>
                <th>Role</th>
                <th>Address</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>${user.userId}</td>
                    <td>${user.username}</td>
                    <td>${user.email}</td>
                    <td>${user.role}</td>
                    <td>${user.address}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>

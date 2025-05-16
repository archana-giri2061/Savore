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

<div class="profile-container">
    <div class="profile-header">
        <img src="${pageContext.request.contextPath}/Resources/Images/System/UserProfile/${admin.image_URL}" 
             alt="Admin Profile Image" 
             onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/Resources/Images/System/UserProfile/default.jpg';">
        <h2>${admin.username}</h2>
        <p>${admin.address}</p>
    </div>

    <c:if test="${not empty successMessage}">
        <div class="success-message">${successMessage}</div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="error-message">${errorMessage}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/AdminProfile" enctype="multipart/form-data">
        <input type="hidden" name="action" value="update">
        <div class="image-upload-container">
            <label for="profileImage">Change Profile Picture:</label><br>
            <input type="file" id="profileImage" name="profileImage" accept="image/*">
        </div>

        <div class="form-row">
            <div class="form-group">
                <label>Username</label>
                <input type="text" name="username" value="${admin.username}" required>
            </div>
            <div class="form-group">
                <label>Full Name</label>
                <input type="text" value="${admin.username}" readonly>
            </div>
        </div>

        <div class="form-row">
            <div class="form-group">
                <label>Email Address</label>
                <input type="email" name="email" value="${admin.email}" required>
            </div>
            <div class="form-group">
                <label>Phone Number</label>
                <input type="text" value="(+98) 9123728167" readonly>
            </div>
        </div>

        <div class="form-row">
            <div class="form-group">
                <label>Location</label>
                <input type="text" name="address" value="${admin.address}">
            </div>
            <div class="form-group">
                <label>Postal Code</label>
                <input type="text" value="23728167" readonly>
            </div>
        </div>

        <div class="form-row">
            <div class="form-group">
                <label>New Password</label>
                <input type="password" name="password" placeholder="Enter new password">
            </div>
        </div>

        <div class="btn-row">
            <button type="submit" name="action" value="update">Save Changes</button>
            <button type="submit" name="action" value="changePassword">Change Password</button>
        </div>
    </form>

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

<jsp:include page="Footer.jsp" />

</body>
</html>

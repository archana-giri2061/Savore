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

<jsp:include page="Header.jsp" />

<div class="profile-container">
    <div class="profile-header">
        <img src="${pageContext.request.contextPath}/Resources/Images/System/UserProfile/${user.image_URL}" 
             alt="Profile Image" 
             onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/Resources/Images/System/UserProfile/default.jpg';">
        <h2>${user.username}</h2>
        <p>${user.address}</p>
    </div>

    <c:if test="${not empty successMessage}">
        <div class="success-message">${successMessage}</div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="error-message">${errorMessage}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/UserProfile" enctype="multipart/form-data">
        <div class="image-upload-container">
            <label for="profileImage">Change Profile Picture:</label><br>
            <input type="file" id="profileImage" name="profileImage" accept="image/*">
        </div>

        <div class="form-row">
            <div class="form-group">
                <label>Name</label>
                <input type="text" name="firstName" value="${user.username}" required>
            </div>
            <div class="form-group">
                <label>Full Name</label>
                <input type="text" value="${user.username}" readonly>
            </div>
        </div>

        <div class="form-row">
            <div class="form-group">
                <label>Email Address</label>
                <input type="email" name="email" value="${user.email}" required>
            </div>
            <div class="form-group">
                <label>Phone Number</label>
                <input type="text" value="(+98) 9123728167" readonly>
            </div>
        </div>

        <div class="form-row">
            <div class="form-group">
                <label>Location</label>
                <input type="text" name="address" value="${user.address}">
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
</div>

<jsp:include page="Footer.jsp" />

</body>
</html>

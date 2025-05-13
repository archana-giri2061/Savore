<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/UserProfile.css">
</head>
<body>
	<div class="container">
        <header>
            <div class="logo">
                    <img src="${pageContext.request.contextPath}/Resources/Images/System/FinalLogo.png" alt="logo">
                </div>
            <nav>
                <ul>
                    <li><a href="#">Home</a></li>
                    <li><a href="#">Cuisine</a></li>
                    <li><a href="#">About</a></li>
                </ul>
            </nav>
        </header>

        <h1>User Profile</h1>
        <form class="profile-form">
            <label for="name">Name</label>
            <input type="text" id="name" value="Jane User">
            <label for="email">Email</label>
            <input type="email" id="email" value="jane.user@example.com">
            <label for="phone">Phone</label>
            <input type="text" id="phone" value="123-456-7890">
            <button>Change Password</button>
        </form>
	<jsp:include page="Footer.jsp" />
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
                </ul>
            </nav>
            <div class="search-bar">
                <input type="text" placeholder="fav food">
                <button>&#128269;</button>
            </div>
        </header>
</body>
</html>
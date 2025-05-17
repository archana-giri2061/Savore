<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Savoré</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/Home.css">
</head>

<body>
    <div class="container">

        <jsp:include page="Header.jsp" />

        <section class="hero">
            <h1>Savor the World at Your Table</h1>
            <p>Discover a symphony of flavors from every corner of the globe, crafted just for you.</p>
            <button>Start exploring</button>
        </section>

        <section class="cuisine-list">
            <h2>Cuisine List</h2>
            <div class="cuisine-grid">
                <c:forEach var="item" items="${cuisineList}">
                    <div class="cuisine-item">
                        <img src="${pageContext.request.contextPath}/Resources/Images/System/Cuisine/${item.imageUrl}" alt="${item.foodName}">
                        <h3>${item.foodName}</h3>
                        <p>${item.description}</p>
                        <p>Origin: ${item.country}</p>
                    </div>
                </c:forEach>
            </div>
        </section>

        <jsp:include page="Footer.jsp" />
    </div>
</body>

</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cuisine Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/CuisinePage.css">
</head>
<body>
<jsp:include page="Header.jsp" />

<c:if test="${sessionScope.orderSuccess}">
    <script>
        alert("✅ Your order has been successfully placed!");
    </script>
    <c:remove var="orderSuccess" scope="session"/>
</c:if>

<div class="container">
    <header>
        <h1>Our Menu</h1>
         <form method="get" action="${pageContext.request.contextPath}/CuisinePage" class="search-bar">
    		<input type="text" name="country" placeholder="Enter country (e.g. Nepal, India)">
    		<button type="submit">&#128269;</button>
		</form>

    </header>
    <section class="menu-grid">
        <c:forEach var="food" items="${foodList}">
            <div class="menu-item">
                <img src="${pageContext.request.contextPath}/Resources/Images/System/Cuisine/${food.imageUrl}" style="width: 50%; height: 300px;" />
                <div class="menu-item-content">
                    <h3>${food.foodName}</h3>
                    <p>${food.description}</p>
                    <p class="price"><fmt:formatNumber value="${food.price}" type="currency"/></p>
                    <form method="get" action="${pageContext.request.contextPath}/orderForm">
                        <input type="hidden" name="foodId" value="${food.foodId}" />
                        <input type="hidden" name="foodName" value="${food.foodName}" />
                        <input type="hidden" name="foodPrice" value="${food.price}" />
                        <button type="submit" class="order-button">Order Now</button>
                    </form>
                </div>
            </div>
        </c:forEach>
    </section>
</div>

<jsp:include page="Footer.jsp" />
</body>
</html>

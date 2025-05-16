<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Place Your Order</title>
    <link rel="stylesheet" href="CSS/CuisinePage.css">
</head>
<body>
<jsp:include page="Header.jsp" />

<div class="container" style="max-width: 600px; margin: auto;">
    <h2 style="font-size: 24px; margin-bottom: 20px;">Order Details</h2>

    <form method="post" action="${pageContext.request.contextPath}/orderForm">
        <input type="hidden" name="foodId" value="${param.foodId}">
        <input type="hidden" name="foodPrice" value="${param.foodPrice}">
        <input type="hidden" name="foodName" value="${param.foodName}">
        
        <div style="margin-bottom: 16px;">
            <label style="font-weight: bold;">Food Item:</label>
            <p style="margin: 4px 0;">${param.foodName}</p>
        </div>
        <div style="margin-bottom: 16px;">
            <label style="font-weight: bold;">Price:</label>
            <p style="margin: 4px 0; color: green;"><fmt:formatNumber value="${param.foodPrice}" type="currency"/></p>
        </div>
        <div style="margin-bottom: 16px;">
            <label for="deliveryAddress" style="font-weight: bold;">Delivery Address:</label><br>
            <textarea name="deliveryAddress" rows="4" cols="50" required placeholder="Enter delivery address here"></textarea>
        </div>
        <div>
            <button type="submit" class="order-button">Place Order</button>
        </div>
    </form>
</div>

<jsp:include page="Footer.jsp" />
</body>
</html>

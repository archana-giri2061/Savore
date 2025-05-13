<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Food List</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <script src="https://unpkg.com/@tailwindcss/browser@latest"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/FoodList.css">
</head>
<body class="bg-gray-100 font-inter">
    <div class="container mx-auto p-6">
        <h1 class="text-3xl font-semibold text-gray-800 mb-6">Admin Food List</h1>

        <div class="flex flex-col md:flex-row gap-4 mb-4">
            <input type="text" placeholder="Search Cuisine" class="input-text" />
            <select class="select-box">
                <option>Country</option>
                <option>Nepal</option>
                <option>India</option>
                <option>China</option>
                <option>Italy</option>
            </select>
        </div>

        <table class="food-table">
            <thead>
                <tr>
                    <th>Food ID</th>
                    <th>Food Name</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Country</th>
                    <th>Image</th>
                    <th>Availability</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody id="food-list-body">
                <c:forEach var="food" items="${foodList}">
                    <tr>
                        <td>${food.foodId}</td>
                        <td>${food.foodName}</td>
                        <td>${food.description}</td>
                        <td><fmt:formatNumber value="${food.price}" type="currency"/></td>
                        <td>${food.country}</td>
                        <td><img src="${pageContext.request.contextPath}/${food.imageUrl}" alt="${food.foodName}" style="width: 100px; height: auto;"></td>
                        <td>${food.availability}</td>
                        <td class="actions">
                            <!-- EDIT -->
                            <form method="get" action="${pageContext.request.contextPath}/AddFoodForm" style="display:inline;">
    							<input type="hidden" name="foodId" value="${food.foodId}" />
    							<input type="hidden" name="foodName" value="${food.foodName}" />
    							<input type="hidden" name="description" value="${food.description}" />
    							<input type="hidden" name="price" value="${food.price}" />
    							<input type="hidden" name="country" value="${food.country}" />
    							<input type="hidden" name="availability" value="${food.availability}" />
    							<input type="hidden" name="imageUrl" value="${food.imageUrl}" />
    							<button type="submit" class="edit-button">Edit</button>
							</form>

                            <!-- DELETE -->
                            <form method="post" action="${pageContext.request.contextPath}/FoodList" style="display:inline;" onsubmit="return confirm('Delete this item?');">
                                <input type="hidden" name="action" value="delete" />
                                <input type="hidden" name="foodId" value="${food.foodId}" />
                                <button type="submit" class="delete-button">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <a href="${pageContext.request.contextPath}/AddFoodForm" class="add-food-button">Add New Food</a>
    </div>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String foodId = request.getParameter("foodId");
    String foodName = request.getParameter("foodName");
    String description = request.getParameter("description");
    String price = request.getParameter("price");
    String country = request.getParameter("country");
    String availability = request.getParameter("availability");
    String imageUrl = request.getParameter("imageUrl");
    boolean isEdit = (foodId != null);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= isEdit ? "Edit Food" : "Add New Food" %></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/AddFood.css">
</head>
<body>
    <div class="form-container">
        <h2><%= isEdit ? "Edit Food Item" : "Add New Food" %></h2>

        <form method="post" action="${pageContext.request.contextPath}/<%= isEdit ? "FoodList?action=edit" : "AddFood" %>" enctype="multipart/form-data">
            
            <%-- Hidden fields for editing --%>
            <% if (isEdit) { %>
                <input type="hidden" name="foodId" value="<%= foodId %>" />
                <input type="hidden" name="existingImageUrl" value="<%= imageUrl %>" />
            <% } %>

            <input type="text" name="foodName" value="<%= foodName != null ? foodName : "" %>" placeholder="Food Name" required />

            <textarea name="description" placeholder="Description" required><%= description != null ? description : "" %></textarea>

            <input type="number" step="0.01" name="price" value="<%= price != null ? price : "" %>" placeholder="Price" required />

            <input type="text" name="country" value="<%= country != null ? country : "" %>" placeholder="Country" required />

            <%-- Image File Input --%>
            <label>Upload Image:</label>
            <input type="file" name="imageFile" accept="image/*" <%= isEdit ? "" : "required" %> />

            <%-- Show preview if editing existing item --%>
            <% if (isEdit && imageUrl != null && !imageUrl.isEmpty()) { %>
                <div style="margin-top: 10px;">
                    <p>Current Image:</p>
                    <img src="${pageContext.request.contextPath}/Resources/Images/System/Cuisine/<%= imageUrl %>" alt="Current Image" style="width: 150px; border-radius: 8px;" />
                </div>
            <% } %>
             

            <select name="availability" required>
                <option value="">Select Availability</option>
                <option value="Available" <%= "Available".equals(availability) ? "selected" : "" %>>Available</option>
                <option value="Unavailable" <%= "Unavailable".equals(availability) ? "selected" : "" %>>Unavailable</option>
            </select>

            <button type="submit"><%= isEdit ? "Update" : "Submit" %></button>
        </form>
    </div>
</body>
</html>

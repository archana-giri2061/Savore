<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/AboutUs.css">
</head>
<body>
	<c:if test="${empty sessionScope.username}">
        <c:redirect url="${pageContext.request.contextPath}/login" />
    </c:if>
	<jsp:include page="Header.jsp" />
	<section class="about-us">
            <div class="about-us-content">
                <h2>About Savoré</h2>
                <p>
                    At Savoré, we believe food is more than just sustenance—it's an
                    experience that connects people to diverse cultures. Our platform
                    brings authentic global flavors directly to your table by
                    partnering with renowned chefs and culinary artisans dedicated
                    to preserving traditional tastes. From the vibrant spices of
                    Southeast Asia to the comforting aromas of the Mediterranean
                    and the refined techniques of European cuisine, Savoré offers a
                    curated selection crafted with passion and quality ingredients.
                    Our mission is to make global cuisine accessible to everyone,
                    celebrating the power of food to unite and delight.
                </p>
            </div>
            <img src="${pageContext.request.contextPath}/Resources/Images/System/AboutUs/AboutUs1.jpg" alt="Japanese Sushi" >
        </section>

        <section class="team-section">
            <h2>Meet the Team</h2>
            <div class="team-grid">
                <div class="team-member">
                    <img src="${pageContext.request.contextPath}/Resources/Images/System/AboutUs/Head.png" alt="Japanese Sushi">
                    <h3>Chief Executive Officer & Visionary</h3>
                    <p>Our CEO, is the visionary behind Savore. With a passion for global cuisine and technology, they have created a seamless food ordering experience that connects food lovers with diverse, delicious dishes from around the world.</p>
                </div>
                <div class="team-member">
                    <img src="${pageContext.request.contextPath}/Resources/Images/System/AboutUs/BestChef.jpg" alt="Japanese Sushi">
                    <h3>Renowned Chef</h3>
                    <p>At Savore, we collaborate with renowned chefs who bring their culinary expertise to our menu. Their passion for crafting diverse, flavorful dishes ensures that every order is a delightful experience, celebrating global cuisine with quality and taste.</p>
                </div>
                <div class="team-member">
                    <img src="${pageContext.request.contextPath}/Resources/Images/System/AboutUs/Patner.jpg" alt="Japanese Sushi">
                    <h3>Partnerships</h3>
                    <p>At Savore, we believe in building strong partnerships with local restaurants and culinary experts. By collaborating with trusted food providers, we ensure that our customers enjoy a wide variety of authentic, high-quality dishes. Join us in our mission to make global cuisine accessible to everyone.</p>
                </div>
            </div>
        </section>
        <jsp:include page="Footer.jsp" />
</body>
</html>
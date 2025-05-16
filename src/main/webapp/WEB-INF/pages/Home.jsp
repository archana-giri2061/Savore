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
                <div class="cuisine-item">
                    <img src="${pageContext.request.contextPath}/Resources/Images/System/Cuisine/sushi.png" alt="Japanese Sushi">
                    <h3>Japanese Sushi</h3>
                    <p>Fresh seafood with rice</p>
                    <p>Perfect for a light meal</p>
                </div>
                <div class="cuisine-item">
                    <img src="${pageContext.request.contextPath}/Resources/Images/System/Cuisine/Thaicurry.jpg" alt="Thai Curry">
                    <h3>Thai Curry</h3>
                    <p>Rich coconut flavors</p>
                    <p>Spicy and aromatic</p>
                </div>
                <div class="cuisine-item">
                    <img src="${pageContext.request.contextPath}/Resources/Images/System/Cuisine/Pasta.jpg" alt="Italian Pasta">
                    <h3>Italian Pasta</h3>
                    <p>Delicious creamy sauce</p>
                    <p>Authentic Italian flavor</p>
                </div>
                <div class="cuisine-item">
                    <img src="${pageContext.request.contextPath}/Resources/Images/System/Cuisine/maxicantacos.jpg" alt="Mexican Tacos">
                    <h3>Mexican Tacos</h3>
                    <p>Savory and crunchy</p>
                    <p>Loaded with flavor</p>
                </div>
                <div class="cuisine-item">
                    <img src="${pageContext.request.contextPath}/Resources/Images/System/Cuisine/IndianCurry.jpg" alt="Indian Curry">
                    <h3>Indian Curry</h3>
                    <p>Generously spiced</p>
                    <p>Served with naan</p>
                </div>
                <div class="cuisine-item">
                    <img src="${pageContext.request.contextPath}/Resources/Images/System/Cuisine/dumplings.jpg" alt="Chinese Dumplings">
                    <h3>Chinese Dumplings</h3>
                    <p>Steamed or fried</p>
                    <p>Savory filling</p>
                </div>
            </div>
        </section>

        <jsp:include page="Footer.jsp" />
    </div>
</body>

</html>

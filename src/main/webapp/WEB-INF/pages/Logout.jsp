<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Logout Confirmation</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(135deg, #007bff, #00c6ff);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .card {
            background: white;
            padding: 30px 40px;
            border-radius: 20px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
            text-align: center;
            width: 300px;
        }

        .card img {
            width: 80px;
            margin-bottom: 20px;
        }

        .card h2 {
            margin: 10px 0;
        }

        .btn {
            display: block;
            width: 100%;
            padding: 12px;
            margin: 10px 0;
            border-radius: 25px;
            font-weight: bold;
            cursor: pointer;
            transition: 0.3s;
        }

        .btn.cancel {
            background-color: #007bff;
            color: white;
            border: none;
        }

        .btn.logout {
            background-color: white;
            color: #007bff;
            border: 2px solid #007bff;
        }

        .btn:hover {
            opacity: 0.9;
        }
    </style>
</head>
<body>
<div class="card">
    <img src="${pageContext.request.contextPath}/Resources/Images/System/logout-icon.png" alt="logout icon">
    <h2>Oh no! You’re leaving...</h2>
    <p>Are you sure?</p>

    <form action="${pageContext.request.contextPath}/LogoutController" method="post">
        <button class="btn logout" type="submit">Yes, Log Me Out</button>
    </form>
    <form action="${pageContext.request.contextPath}/AdminDashboard" method="get">
        <button class="btn cancel" type="submit">Naah, Just Kidding</button>
    </form>
</div>
</body>
</html>

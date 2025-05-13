<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/AdminProfile.css">
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
        
        <h1>Admin Profile</h1>
        <form class="profile-form">
            <label for="name">Name</label>
            <input type="text" id="name" value="John Doe">
            <label for="email">Email</label>
            <input type="email" id="email" value="john.doe@example.com">
            <label for="role">Role</label>
            <input type="text" id="role" value="Super Admin">
            <button>Change Password</button>
        </form>

        <section class="manage-admins">
            <h2>Manage other Admins</h2>
            <table class="admin-list">
                <thead>
                    <tr>
                        <th>Email</th>
                        <th>Role</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>uegwkasdbj@gmail.com</td>
                        <td>Super Admin</td>
                        <td class="actions">
                            <button class="edit">Edit</button>
                            <button class="delete">Delete</button>
                        </td>
                    </tr>
                    <tr>
                        <td>jhasgbdsanb@yahoo.com</td>
                        <td>Admin</td>
                         <td class="actions">
                            <button class="edit">Edit</button>
                            <button class="delete">Delete</button>
                        </td>
                    </tr>
                    <tr>
                        <td>wkjdf.mbads@gmail.com</td>
                        <td>Admin</td>
                         <td class="actions">
                            <button class="edit">Edit</button>
                            <button class="delete">Delete</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </section>
	<jsp:include page="Footer.jsp" />
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - Savoré</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js@3.9.1/dist/chart.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/AdminDashboard.css">
</head>
<body class="bg-gray-100 font-inter">
	 <c:if test="${empty sessionScope.admin}">
        <c:redirect url="/admin/login.jsp"/>
    </c:if>
	
	<div class="flex">
    <aside class="bg-white w-64 p-4 shadow-md">
        <div class="logo text-xl font-semibold text-gray-800 mb-6">Savoré</div>
        <nav class="space-y-2">
            <a href="${pageContext.request.contextPath}/FoodList" class="block px-4 py-2 text-gray-700 hover:bg-gray-100 rounded-md">Food List</a>
            <a href="${pageContext.request.contextPath}/AddFood" class="block px-4 py-2 text-gray-700 hover:bg-gray-100 rounded-md">Add Food</a>
            <a href="${pageContext.request.contextPath}/AdminProfile" class="block px-4 py-2 text-gray-700 hover:bg-gray-100 rounded-md">Profile</a>
            <a href="${pageContext.request.contextPath}/admin/logout" class="block px-4 py-2 text-gray-700 hover:bg-gray-100 rounded-md">Logout</a>
        </nav>
    </aside>

    <main class="flex-1 p-6 overflow-y-auto">
        <header class="flex justify-between items-center mb-8">
                <h1 class="text-2xl font-semibold text-gray-800">Admin Dashboard</h1>
                <div class="flex items-center">
                    <span class="mr-4 text-gray-600">Welcome, ${sessionScope.admin.username}</span>
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-gray-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9" />
                    </svg>
                </div>
            </header>
             <c:if test="${not empty error}">
                <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-4" role="alert">
                    ${error}
                </div>
            </c:if>
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
            <div class="bg-white shadow-md rounded-md p-4 text-center">
                <div class="text-xl font-semibold text-gray-700 mb-2">Total Users</div>
                <div class="text-2xl font-bold text-blue-600" id="total-users">${totalUsers}</div>
                <canvas id="total-users-chart" class="mt-2 mx-auto" width="150" height="150"></canvas>
            </div>
            <div class="bg-white shadow-md rounded-md p-4 text-center">
                <div class="text-xl font-semibold text-gray-700 mb-2">New Registrations</div>
                <div class="text-2xl font-bold text-purple-600" id="new-signups">${newSignups}</div>
            </div>
            <div class="bg-white shadow-md rounded-md p-4 text-center">
                <div class="text-xl font-semibold text-gray-700 mb-2">Total Revenue</div>
                <div class="text-2xl font-bold text-black" id="total-revenue">$<fmt:formatNumber value="${totalRevenue}" pattern="#,##0.00"/></div>
                <canvas id="revenue-bar-chart" class="mt-2 mx-auto" width="200" height="150"></canvas>
            </div>
            <div class="bg-white shadow-md rounded-md p-4 text-center">
                <div class="text-xl font-semibold text-gray-700 mb-2">Total Orders</div>
                <div class="
                text-2xl font-bold text-green-600" id="total-orders">${totalOrders}</div>
            </div>
        </div>
        	

        <section class="bg-white shadow-md rounded-md p-4 mt-10">
            <h2 class="text-xl font-semibold text-gray-800 mb-4">Recent Orders</h2>
            <div class="overflow-x-auto">
                <table class="min-w-full divide-y divide-gray-200">
                    <thead class="bg-gray-50">
                        <tr>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Order ID</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">User Name</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Food Items</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Total Amount</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Order Date</th>
                        </tr>
                    </thead>
                    <tbody class="bg-white divide-y divide-gray-200">
                        <c:forEach var="order" items="${recentOrders}">
                            <tr>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">${order.orderId}</td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">${order.userName}</td>
                                <td class="px-6 py-4 text-sm text-gray-500">${order.foodItems}</td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">$<fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00"/></td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm ${order.status == 'Delivered' ? 'text-green-500' : order.status == 'Cancelled' ? 'text-red-500' : 'text-yellow-500'}">${order.status}</td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500"><fmt:formatDate value="${order.orderDate}" pattern="dd MMM yyyy HH:mm"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <c:if test="${empty recentOrders}">
                <p class="mt-4 text-gray-500">No recent orders found.</p>
            </c:if>
        </section>
    </main>
</div>

<script>
    document.addEventListener('DOMContentLoaded', () => {
        const totalUsers = parseInt(document.getElementById('total-users')?.textContent || 0);
        const newUsers = parseInt(document.getElementById('new-signups')?.textContent || 0);
        const oldUsers = totalUsers - newUsers;

        const usersChart = new Chart(document.getElementById('total-users-chart').getContext('2d'), {
            type: 'pie',
            data: {
                labels: ['Existing Users', 'New Users (3 days)'],
                datasets: [{
                    data: [oldUsers, newUsers],
                    backgroundColor: ['#d1d5db', '#3b82f6']
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'bottom'
                    }
                }
            }
        });

        const totalRevenue = parseFloat(document.getElementById('total-revenue')?.textContent.replace('$', '') || 0);

        const revenueChart = new Chart(document.getElementById('revenue-bar-chart').getContext('2d'), {
            type: 'bar',
            data: {
                labels: ['Total Revenue'],
                datasets: [{
                    label: 'Revenue',
                    data: [totalRevenue],
                    backgroundColor: ['#f59e0b']
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                },
                plugins: {
                    legend: {
                        display: false
                    }
                }
            }
        });
    });
</script>
</body>
</html>

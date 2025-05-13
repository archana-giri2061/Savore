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

    <div class="flex h-screen">
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

            <section class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
                <div class="bg-white shadow-md rounded-md p-4 flex flex-col items-center">
                    <div class="text-xl font-semibold text-gray-700 mb-2">Total Users</div>
                    <div class="text-2xl font-bold text-blue-600" id="total-users">${totalUsers}</div>
                    <div class="text-sm" id="total-users-percentage">
                        <c:choose>
                            <c:when test="${totalUsersPercentage >= 0}">
                                <span class="text-green-500">+${totalUsersPercentage}%</span>
                            </c:when>
                            <c:otherwise>
                                <span class="text-red-500">${totalUsersPercentage}%</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="chart-container mt-4 w-32 h-32">
                        <canvas id="total-users-chart"></canvas>
                    </div>
                </div>
                <div class="bg-white shadow-md rounded-md p-4 flex flex-col items-center">
                    <div class="text-xl font-semibold text-gray-700 mb-2">New Registrations</div>
                    <div class="text-2xl font-bold text-purple-600" id="new-signups">${newSignups}</div>
                    <div class="text-sm" id="new-signups-percentage">
                        <c:choose>
                            <c:when test="${newSignupsPercentage >= 0}">
                                <span class="text-green-500">+${newSignupsPercentage}%</span>
                            </c:when>
                            <c:otherwise>
                                <span class="text-red-500">${newSignupsPercentage}%</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="chart-container mt-4 w-32 h-32">
                        <canvas id="new-signups-chart"></canvas>
                    </div>
                </div>
                <div class="bg-white shadow-md rounded-md p-4 flex flex-col items-center">
                    <div class="text-xl font-semibold text-gray-700 mb-2">Active Sessions</div>
                    <div class="text-2xl font-bold text-green-600" id="active-sessions">${activeSessions}</div>
                    <div class="text-sm" id="active-sessions-percentage">
                        <c:choose>
                            <c:when test="${activeSessionsPercentage >= 0}">
                                <span class="text-green-500">+${activeSessionsPercentage}%</span>
                            </c:when>
                            <c:otherwise>
                                <span class="text-red-500">${activeSessionsPercentage}%</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="chart-container mt-4 w-32 h-32">
                        <canvas id="active-sessions-chart"></canvas>
                    </div>
                </div>
                <div class="bg-white shadow-md rounded-md p-4">
                    <div class="text-xl font-semibold text-gray-700">Total Revenue</div>
                    <div class="text-2xl font-bold text-orange-600" id="total-revenue">$<fmt:formatNumber value="${totalRevenue}" pattern="#,##0.00"/></div>
                    <div class="text-sm" id="total-revenue-percentage">
                        <c:choose>
                            <c:when test="${totalRevenuePercentage >= 0}">
                                <span class="text-green-500">+${totalRevenuePercentage}%</span>
                            </c:when>
                            <c:otherwise>
                                <span class="text-red-500">${totalRevenuePercentage}%</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="bg-white shadow-md rounded-md p-4">
                    <div class="text-xl font-semibold text-gray-700">Total Orders</div>
                    <div class="text-2xl font-bold text-green-600" id="total-orders">${totalOrders}</div>
                    <div class="text-sm" id="total-orders-percentage">
                        <c:choose>
                            <c:when test="${totalOrdersPercentage >= 0}">
                                <span class="text-green-500">+${totalOrdersPercentage}%</span>
                            </c:when>
                            <c:otherwise>
                                <span class="text-red-500">${totalOrdersPercentage}%</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </section>

            <section class="bg-white shadow-md rounded-md p-4">
                <h2 class="text-xl font-semibold text-gray-800 mb-4">Recent Orders</h2>
                <div class="overflow-x-auto">
                    <table class="min-w-full divide-y divide-gray-200">
                        <thead class="bg-gray-50">
                            <tr>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Order ID</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">User Name</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Food Items</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Total Amount</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Order Date</th>
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
                                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                                        <fmt:formatDate value="${order.orderDate}" pattern="dd MMM yyyy HH:mm"/>
                                    </td>
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

    <footer class="bg-gray-200 text-center py-4 text-sm text-gray-600">
        Copyright © 2025 Savoré | Need help? <a href="mailto:support@savore.com" class="underline">Contact Support</a>
    </footer>

    <script>
        // Chart configurations
        const chartConfig = {
            pie: {
                responsive: true,
                maintainAspectRatio: true,
                plugins: {
                    legend: {
                        position: 'top',
                        labels: {
                            font: { size: 10 }
                        }
                    }
                }
            },
            bar: {
                responsive: true,
                maintainAspectRatio: true,
                scales: {
                    y: { beginAtZero: true }
                },
                plugins: {
                    legend: {
                        position: 'top',
                        labels: {
                            font: { size: 10 }
                        }
                    }
                }
            }
        };

        // Total Users Pie Chart
        const usersChart = new Chart(document.getElementById('total-users-chart').getContext('2d'), {
            type: 'pie',
            data: {
                labels: ['Subscribed Users', 'Non-Subscribed Users'],
                datasets: [{
                    data: [0, 0],
                    backgroundColor: ['#3498db', '#e0e0e0'],
                    borderWidth: 0
                }]
            },
            options: chartConfig.pie
        });

        // New Signups Bar Chart
        const signupsChart = new Chart(document.getElementById('new-signups-chart').getContext('2d'), {
            type: 'bar',
            data: {
                labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                datasets: [{
                    label: 'New Registrations',
                    data: [0, 0, 0, 0, 0, 0, 0],
                    backgroundColor: '#8e44ad',
                    borderWidth: 1
                }]
            },
            options: chartConfig.bar
        });

        // Active Sessions Pie Chart
        const activeSessionsChart = new Chart(document.getElementById('active-sessions-chart').getContext('2d'), {
            type: 'pie',
            data: {
                labels: ['Mobile Users', 'Desktop Users'],
                datasets: [{
                    data: [0, 0],
                    backgroundColor: ['#2ecc71', '#f39c12'],
                    borderWidth: 0
                }]
            },
            options: chartConfig.pie
        });

        // Update dashboard with fetched data
        function updateDashboard(data) {
            // Update metrics
            const metrics = [
                { id: 'total-users', value: data.totalUsers, percentage: data.totalUsersPercentage },
                { id: 'new-signups', value: data.newSignups, percentage: data.newSignupsPercentage },
                { id: 'active-sessions', value: data.activeSessions, percentage: data.activeSessionsPercentage },
                { id: 'total-orders', value: data.totalOrders, percentage: data.totalOrdersPercentage },
                { id: 'total-revenue', value: `$${parseFloat(data.totalRevenue).toFixed(2)}`, percentage: data.totalRevenuePercentage }
            ];

            metrics.forEach(metric => {
                const valueEl = document.getElementById(metric.id);
                const percentageEl = document.getElementById(`${metric.id}-percentage`);
                
                if (valueEl && percentageEl) {
                    valueEl.textContent = metric.value;
                    percentageEl.innerHTML = `${metric.percentage >= 0 ? '+' : ''}${metric.percentage}%`;
                    percentageEl.className = `text-sm ${metric.percentage >= 0 ? 'text-green-500' : 'text-red-500'}`;
                }
            });

            // Update charts
            usersChart.data.datasets[0].data = [data.userCounts.subscribed, data.userCounts.nonSubscribed];
            usersChart.update();

            signupsChart.data.datasets[0].data = data.weeklySignups;
            signupsChart.update();

            activeSessionsChart.data.datasets[0].data = [data.deviceCounts.mobile, data.deviceCounts.desktop];
            activeSessionsChart.update();
        }

        // Fetch dashboard data
        async function fetchDashboardData() {
            try {
                const response = await fetch('${pageContext.request.contextPath}/admin/dashboard_data', {
                    headers: {
                        'Accept': 'application/json'
                    }
                });
                
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }

                const data = await response.json();
                updateDashboard(data);
            } catch (error) {
                console.error('Error fetching dashboard data:', error);
                const errorElements = ['total-users', 'new-signups', 'active-sessions', 'total-orders', 'total-revenue'];
                errorElements.forEach(id => {
                    const el = document.getElementById(id);
                    if (el) el.textContent = 'Error';
                });
                
                // Show error notification
                const errorDiv = document.createElement('div');
                errorDiv.className = 'bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-4';
                errorDiv.textContent = 'Failed to load dashboard data. Please try again later.';
                document.querySelector('main').prepend(errorDiv);
            }
        }

        // Initialize dashboard
        document.addEventListener('DOMContentLoaded', fetchDashboardData);
    </script>
</body>
</html>
<%@ page import="ui.UserInfo" %>
<%@ page import="ui.OrderInfo" %>
<%@ page import="ui.OrderItemInfo" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="businessObjects.OrderHandler" %>
<%@ page import="businessObjects.Authority" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/navBarStyle.css">
    <link rel="stylesheet" href="css/orderStyle.css"> <!-- The CSS file containing your toggle styles -->
    <title>Orders</title>
</head>
<body>
<% UserInfo currentUser = (UserInfo) session.getAttribute("currentUser"); %>

<div class="topnav">
    <a class="active" href="shop.jsp">Home</a>
    <a href="orders.jsp">Orders</a>
    <a href="products.jsp">Products</a>
    <% if(currentUser != null && currentUser.getAuthority() == Authority.Admin) { %>
    <a href="users.jsp">Users</a>
    <% } %>
    <% if(currentUser == null) { %>
    <a href="login.jsp">Login</a>
    <% } else { %>
    <form action="login-servlet" method="POST">
        <input type="hidden" name="action" value="logout">
        <button class="log-out" type="submit">Logout</button>
    </form>
    <a class="view-user">Logged in: <%= currentUser.getName()%></a>
    <% } %>
</div>

<div class="order-list-container">
    <h2>All Orders</h2>

    <!-- Fetch all orders from the system -->
    <%
        ArrayList<OrderInfo> orders = OrderHandler.getAllOrders();
        if (orders == null || orders.isEmpty()) {
    %>
    <p>No orders found.</p>
    <% } else { %>

    <!-- Orders list -->
    <ul class="order-list">
        <%
            for (OrderInfo order : orders) {
                int orderNr = order.getOrderNr();
                String userEmail = order.getUserEmail();
                String status = order.getOrderStatus().toString();
                ArrayList<OrderItemInfo> orderItems = order.getOrderItemInfo();
        %>
        <li>
            <!-- Display order information -->
            <strong>Order Number:</strong> <%= orderNr %> <br>
            <strong>User Email:</strong> <%= userEmail %> <br>
            <strong>Order Status:</strong> <%= status %> <br>
            <strong>Number of Items:</strong> <%= orderItems.size() %> <br>

            <!-- Checkbox to toggle order items -->
            <input type="checkbox" id="toggleOrder<%= orderNr %>" hidden>
            <label for="toggleOrder<%= orderNr %>" class="view-order-items">View Order Items</label>

            <!-- Nested list for order items (hidden by default, shown when checkbox is checked) -->
            <ul class="order-items-list order-details-<%= orderNr %>">
                <% for (OrderItemInfo item : orderItems) { %>
                <li>
                    <strong>Item ID:</strong> <%= item.getItemId() %> <br>
                    <strong>Quantity:</strong> <%= item.getNrOfItems() %>
                </li>
                <% } %>
            </ul>
        </li>
        <% } %>
    </ul>
    <% } %>
</div>
</body>
</html>

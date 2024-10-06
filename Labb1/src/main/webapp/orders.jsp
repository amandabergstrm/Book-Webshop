<%@ page import="ui.UserInfo" %>
<%@ page import="ui.OrderInfo" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="businessObjects.OrderHandler" %>
<%@ page import="businessObjects.Authority" %>
<%@ page import="ui.OrderItemInfo" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Collection" %>
<%@ page import="businessObjects.OrderStatus" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/navBarStyle.css">
    <link rel="stylesheet" href="css/orderStyle.css">
    <link rel="stylesheet" href="css/handleBookForms.css">
    <title>Manage Orders</title>
</head>
<body>
<% UserInfo currentUser = (UserInfo) session.getAttribute("currentUser"); %>


<div class="topnav">
    <a class="active" href="shop-servlet">Home</a>
    <a href="orders-servlet">Orders</a>
    <a href="product-servlet">Products</a>
    <% if(currentUser != null && currentUser.getAuthority() == Authority.Admin) { %>
    <a href="user-servlet">Users</a>
    <% } %>
    <a href="profile.jsp">Profile</a>
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

<div class="order-list">
    <div class="list-order">
        <div class="order-info">
            <a> </a>
            <a>Order number</a>
            <a>User email</a>
            <a>Order Status</a>
            <a>Handle order</a>
        </div>
    </div>

        <%ArrayList<OrderInfo> ordersInfo = (ArrayList<OrderInfo>) request.getAttribute("ordersInfo");
        for(OrderInfo o: ordersInfo){
        OrderInfo orderInfo = o;
        int orderNr = orderInfo.getOrderNr();
        ArrayList<OrderItemInfo> orderItems = orderInfo.getOrderItemInfo(); %>
        %>

    <div class="list-order">
        <div class="order-info">
            <p></p>
            <p><%= orderNr %></p>
            <p><%= orderInfo.getUserEmail() %></p>
            <p><%= orderInfo.getOrderStatus() %></p>


            <!-- Checkbox to toggle the visibility of order items -->
            <input type="checkbox" id="toggleOrder<%= orderNr %>" hidden>
            <label for="toggleOrder<%= orderNr %>" class="view-order-items">View Order Items</label>

            <!-- Nested list for order items, hidden by default -->
            <ul class="order-items-list" id="orderItems<%= orderNr %>">
                <% for (OrderItemInfo item : orderItems) { %>
                <li>
                    <strong>Item ID:</strong> <%= item.getItemId() %> <br>
                    <strong>Quantity:</strong> <%= item.getNrOfItems() %>
                </li>
                <% } %>
            </ul>

            <!-- Edit Button -->
            <input type="checkbox" id="editOrderStatusToggle<%=orderNr%>" hidden>
            <div class="form-container" id="editOrderStatusForm<%=orderNr%>">
                <label for="editOrderStatusToggle<%=orderNr%>" class="close-btn">&times;</label>
                <h2>Edit user: <%=orderNr%></h2>
                <form action="orders-servlet" method="POST">
                    <input type="hidden" name="action" value="edit">
                    <input type="hidden" name="orderNr" value="<%=orderNr%>">
                    <label for="newOrderStatus<%=orderNr%>">New order status (leave empty to keep unchanged):</label>
                    <select id="newOrderStatus<%=orderNr%>" name="status" required>
                        <% for (OrderStatus status : OrderStatus.values()) { %>
                        <option value="<%= status %>"><%= status %></option>
                        <% } %>
                    </select>
                    <button type="submit">Save Changes</button>
                </form>
            </div>
            <label for="editOrderStatusToggle<%=orderNr%>" class="toggle-link">Edit</label>
        </div>
    </div>
        <% } %>
</body>
</html>
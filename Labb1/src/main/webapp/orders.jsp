<%@ page import="ui.UserInfo" %>
<%@ page import="ui.OrderInfo" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="businessObjects.OrderHandler" %>
<%@ page import="businessObjects.Authority" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/navBarStyle.css">
    <link rel="stylesheet" href="css/orderStyle.css">
    <title>Manage Orders</title>
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

    <!-- Hämta alla ordrar -->
    <% ArrayList<OrderInfo> orderInfos = new ArrayList<OrderInfo>()

</div>

</body>
</html>
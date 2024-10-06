<%@ page import="ui.UserInfo" %>
<%@ page import="ui.OrderInfo" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="businessObjects.OrderHandler" %>
<%@ page import="businessObjects.Authority" %>
<%@ page import="ui.OrderItemInfo" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Collection" %>
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

    <!-- Hämta alla ordrar -->

    <!-- < % ArrayList<OrderInfo> ordersInfo = (ArrayList<OrderInfo>) request.getSession().getAttribute("ordersInfo");
        for(OrderInfo o: ordersInfo){
            OrderInfo orderInfo = o;
            int orderNr = orderInfo.getOrderNr();
            %>

            <div class="list-order">
                <div class="order-info">
                <p>< %= orderNr %></p>
                <p>< %= orderInfo.getUserEmail() %></p>
                    <p>< %= orderInfo.getOrderStatus() %></p>
                    < % } %>
</div>
            </div> -->

    <!-- Ändra order status knapp -->

        <%  Collection<OrderInfo> orderInfos = (Collection<OrderInfo>) request.getAttribute("ordersInfo");
            Iterator<OrderInfo> it = orderInfos.iterator();
            for (; it.hasNext();) {
                OrderInfo orderInfo = it.next();
                int orderNr = orderInfo.getOrderNr();
        %>
    <div class="list-order">
        <div class="order-info">
            <p></p>
            <p><%= orderNr %></p>
            <p><%= orderInfo.getUserEmail() %></p>
            <p><%= orderInfo.getOrderStatus() %></p>


</div>

    </div>
        <% } %>
</body>
</html>
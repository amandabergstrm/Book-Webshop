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

    <!-- Hårdkodat alla current orders, ta bort sen, bara kontroll att den kommer till orders.jsp överhuvudtaget-->
    <div>
        <h2>Display all current orders</h2>
        <%
            ArrayList<OrderInfo> orders = OrderHandler.getAllOrders(); // Fetch all orders
            Iterator<OrderInfo> orderIterator = orders.iterator(); // Create an iterator
            if (!orders.isEmpty()) {
        %>
        <table>
            <thead>
            <tr>
                <th>Order Number</th>
                <th>User Email</th>
                <th>Order Status</th>
                <th>Number of Items</th>
                <th>Order Items</th>
            </tr>
            </thead>
            <tbody>
            <%
                while (orderIterator.hasNext()) {
                    OrderInfo order = orderIterator.next();
                    int orderNr = order.getOrderNr();
                    String userEmail = order.getUserEmail();
                    String status = order.getOrderStatus().toString();
                    ArrayList<OrderItemInfo> orderItems = order.getOrderItemInfo();
            %>
            <tr>
                <td><%= orderNr %></td>
                <td><%= userEmail %></td>
                <td><%= status %></td>
                <td><%= orderItems.size() %></td>
                <td>
                    <ul>
                        <% for (OrderItemInfo item : orderItems) { %>
                        <li>Item ID: <%= item.getItemId() %>, Quantity: <%= item.getNrOfItems() %></li>
                        <% } %>
                    </ul>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
        <%
        } else {
        %>
        <p>No orders found.</p>
        <%
            }
        %>
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

        <%  Collection<OrderInfo> orderInfos = (Collection<OrderInfo>) request.getSession().getAttribute("ordersInfo");
            Iterator<OrderInfo> it = orderInfos.iterator();
            for (; it.hasNext();) {
                OrderInfo orderInfo = it.next();
                int orderNr = orderInfo.getOrderNr();
        %>
    <div class="list-order">
        <div class="order-info">
            <p><%= orderNr %></p>
            <p><%= orderInfo.getUserEmail() %></p>
            <p><%= orderInfo.getOrderStatus() %></p>

            <% } %>
</div>
    </div>
</body>
</html>
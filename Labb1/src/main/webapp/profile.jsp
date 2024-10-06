<%@ page import="ui.UserInfo" %>
<%@ page import="businessObjects.Authority" %>
<%@ page import="ui.OrderItemInfo" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ui.OrderInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/navBarStyle.css">
    <link rel="stylesheet" href="css/sidebarCartStyle.css">
    <link rel="stylesheet" href="css/profileStyle.css">
    <link rel="stylesheet" href="css/orderStyle.css">
    <title>Profile</title>
</head>
<body>
    <% UserInfo currentUser = (UserInfo) request.getSession().getAttribute("currentUser"); %>

    <div class="topnav">
        <a class="active" href="shop-servlet">Home</a>
        <% if (currentUser != null && currentUser.getAuthority() != Authority.Customer) { %>
        <a href="orders-servlet">Orders</a>
        <a href="product-servlet">Products</a>
        <% } %>
        <% if (currentUser != null && currentUser.getAuthority() == Authority.Admin) { %>
        <a href="user-servlet">Users</a>
        <% } %>
        <a href="profile-servlet">Profile</a>
        <a class="cart-link"><label for="cartToggle" class="open-link">View Cart</label></a>
        <% if (currentUser == null) { %>
        <a href="login.jsp">Login</a>
        <% } else { %>
        <form action="login-servlet" method="POST">
            <input type="hidden" name="action" value="logout">
            <button class="log-out" type="submit">Logout</button>
        </form>
        <a class="view-user">Logged in: <%= currentUser.getName() %></a>
        <% } %>
    </div>

    <input type="checkbox" id="cartToggle" hidden>

    <div id="cartSidebar" class="cart-sidebar">
        <label for="cartToggle" class="closebtn">&times;</label>
        <h2>Your Cart</h2>
        <div id="cartItems">
            <%  session = request.getSession();
                ArrayList<OrderItemInfo> cart = (ArrayList<OrderItemInfo>) session.getAttribute("cart");
                if (cart == null || cart.isEmpty()) { %>
            <p class="empty-cart">Your cart is empty!</p>
            <% } else { %>
            <p>
                    <% for (OrderItemInfo itemInfo : cart) { %>
            <p> <%= itemInfo.getItem().getTitle() %>   -   <%= itemInfo.getItem().getPrice() %> kr  -  <%= itemInfo.getNrOfItems()%> </p>
            <% } %>
            </p>
            <% } %>
        </div>
        <% if (cart != null && !cart.isEmpty()) { %>
        <label class="proceedToPay">
            <% if (currentUser == null) {%>
            <a href="login.jsp">Proceed to Pay</a>
            <% } else { %>
            <a href="orderSummary.jsp">Proceed to Pay</a>
            <% } %>
        </label>
        <% } %>
    </div>

    <div class="profile-container">
        <div class="user-info">
            <h2>My Profile</h2>
            <p><strong>Name:</strong> <%= currentUser.getName() %></p>
            <p><strong>Email:</strong> <%= currentUser.getEmail() %></p>
        </div>

        <!-- User Orders -->
        <div class="orders-section">
            <h2>Your Orders</h2>

            <% ArrayList<OrderInfo> userOrders = (ArrayList<OrderInfo>) request.getAttribute("userOrders");
                for(OrderInfo orderInfo : userOrders) {
                    int orderNr = orderInfo.getOrderNr();
                    ArrayList<OrderItemInfo> orderItems = orderInfo.getOrderItemInfo(); %>

            <div class="list-order">
                <div class="order-info">
                    <p></p>
                    <p><%= orderNr %></p>
                    <p><%= orderInfo.getUserEmail() %></p>
                    <p><%= orderInfo.getOrderStatus() %></p>
                    <p></p>
                    <p></p>

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
                </div>
            </div>
            <% } %>
        </div>
    </div>
</body>
</html>

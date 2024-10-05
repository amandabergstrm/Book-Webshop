<%@ page import="ui.UserInfo" %>
<%@ page import="businessObjects.Authority" %>
<%@ page import="ui.OrderItemInfo" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/navBarStyle.css">
    <link rel="stylesheet" href="css/sidebarCartStyle.css">
    <link rel="stylesheet" href="css/profileStyle.css">
    <title>Profile</title>
</head>
<body>
    <% UserInfo currentUser = (UserInfo) request.getSession().getAttribute("currentUser"); %>

    <div class="topnav">
        <a class="active" href="shop.jsp">Home</a>
        <% if (currentUser != null && currentUser.getAuthority() != Authority.Customer) { %>
        <a href="orders.jsp">Orders</a>
        <a href="products.jsp">Products</a>
        <% } %>
        <% if (currentUser != null && currentUser.getAuthority() == Authority.Admin) { %>
        <a href="users.jsp">Users</a>
        <% } %>
        <a href="profile.jsp">Profile</a>
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
            <p>Your cart is empty!</p>
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
            <!-- samma kod som från orders -->
        </div>
    </div>
</body>
</html>

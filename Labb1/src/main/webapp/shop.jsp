<%@ page import="businessObjects.UserHandler" %>
<%@ page import="ui.UserInfo" %>
<%@ page import="ui.BookInfo" %>
<%@ page import="businessObjects.BookHandler" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Collection" %>
<%@ page import="businessObjects.Authority" %>
<%@ page import="ui.OrderItemInfo" %>
<%@ page import="ui.OrderItemInfo" %>
<%@ page import="ui.OrderInfo" %>
<%@ page import="businessObjects.OrderHandler" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/mainPageStyle.css">
    <link rel="stylesheet" href="css/shopItemsStyle.css">
    <link rel="stylesheet" href="css/navBarStyle.css">
    <link rel="stylesheet" href="css/sidebarCartStyle.css">
</head>

<body>
    <% UserInfo currentUser = (UserInfo) request.getSession().getAttribute("currentUser"); %>

    <% if (currentUser == null) {
        currentUser = new UserInfo(Authority.Admin, "Test User", "testuser@example.com", "password123");
        session.setAttribute("currentUser", currentUser);
    } %>

<!-- Navigation bar -->
<div class="topnav">
    <a class="active" href="shop.jsp">Home</a>
    <% if (currentUser != null && currentUser.getAuthority() != Authority.Customer) { %>
    <a href="orders.jsp">Orders</a>
    <a href="products.jsp">Products</a>
    <% } %>
    <% if (currentUser != null && currentUser.getAuthority() == Authority.Admin) { %>
    <a href="users.jsp">Users</a>
    <% } %>
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

    <div class="shopTitle">
        <h1><%= "The Bookshop" %></h1>
    </div>

    <div class="shop-container">
        <%  Collection<BookInfo> books = (Collection<BookInfo>) request.getSession().getAttribute("booksInfo");
            Iterator<BookInfo> it = books.iterator();
            for (; it.hasNext();) {
                BookInfo b = it.next();
                int nrOfCopies = b.getNrOfCopies();
        %>
            <div class="shop-item">
                <div class="item-info">
                    <img src="resources/<%= b.getTitle()%>.jpg" alt="<%= b.getTitle()%>"> <!--img samma med bild om dem lagras-->
                    <h3><%= b.getTitle()%></h3>
                    <p><%= b.getAuthor()%></p>
                    <isbn>Genre: <%= b.getGenre()%></isbn>
                    <isbn>ISBN: <%= b.getIsbn()%></isbn>

                    <status>
                        <% if (nrOfCopies == 0) { %>
                        <span style="color: red; font-weight: bold;">●</span> Not available
                        <% } else if (nrOfCopies < 10) { %>
                        <span style="color: orange; font-weight: bold;">●</span> A few left
                        <% } else { %>
                        <span style="color: green; font-weight: bold;">●</span> Available
                        <% } %>
                    </status>

                    <p><%= b.getPrice()%> kr</p>
                    <form action="cart-servlet" method="POST">
                        <input type="hidden" name="itemId" value="<%= b.getItemId()%>">
                        <button type="submit" class="add-to-cart-button" <%= (nrOfCopies == 0) ? "disabled" : "" %> >Add to Cart</button>
                    </form>
                </div>
            </div>
        <%}%>
    </div>
</body>

<!-- Display all current orders -->
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

<!-- Create a new order -->
<div>
    <h2>Create and Display a New Order</h2>
    <%
        // Retrieve a book to create a new order
        ArrayList<BookInfo> booksTest = (ArrayList<BookInfo>) BookHandler.getAllBooks();

        int itemIdTest = booksTest.get(0).getItemId(); // Get the first book's itemId
        OrderItemInfo orderItemInfo = new OrderItemInfo(itemIdTest, 13); // 13 is the quantity

        // Create OrderItemInfo list and OrderInfo
        ArrayList<OrderItemInfo> orderItemInfos = new ArrayList<>();
        orderItemInfos.add(orderItemInfo);

        OrderInfo newOrder = new OrderInfo(currentUser.getEmail(), orderItemInfos);
        OrderHandler.createOrder(newOrder); // Create the new order
    %>

    <!-- Fetch and display updated orders -->
    <div>
        <h2>Updated Orders List After Creating a New Order</h2>
        <%
            ArrayList<OrderInfo> updatedOrders = OrderHandler.getAllOrders(); // Fetch updated orders
            Iterator<OrderInfo> updatedOrderIterator = updatedOrders.iterator(); // Create iterator
            if (!updatedOrders.isEmpty()) {
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
                while (updatedOrderIterator.hasNext()) {
                    OrderInfo updatedOrder = updatedOrderIterator.next();
                    int updatedOrderNr = updatedOrder.getOrderNr();
                    String updatedUserEmail = updatedOrder.getUserEmail();
                    String updatedStatus = updatedOrder.getOrderStatus().toString();
                    ArrayList<OrderItemInfo> updatedOrderItemsList = updatedOrder.getOrderItemInfo();
            %>
            <tr>
                <td><%= updatedOrderNr %></td>
                <td><%= updatedUserEmail %></td>
                <td><%= updatedStatus %></td>
                <td><%= updatedOrderItemsList.size() %></td>
                <td>
                    <ul>
                        <% for (OrderItemInfo item : updatedOrderItemsList) { %>
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
        <p>No updated orders found.</p>
        <%
            }
        %>
    </div>
</div>
<body>
</body>
</html>
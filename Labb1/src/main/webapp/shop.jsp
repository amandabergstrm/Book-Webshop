<%@ page import="ui.UserInfo" %>
<%@ page import="ui.BookInfo" %>
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

    <!-- Navigation bar -->
    <div class="topnav">
        <a class="active" href="shop-servlet">Home</a>
        <% if (currentUser != null && currentUser.getAuthority() != Authority.Customer) { %>
        <a href="orders-servlet">Orders</a>
        <a href="product-servlet">Products</a>
        <% } %>
        <% if (currentUser != null && currentUser.getAuthority() == Authority.Admin) { %>
        <a href="user-servlet">Users</a>
        <% } %>
        <% if (currentUser != null) { %>
        <a href="profile-servlet">Profile</a>
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

    <input type="checkbox" id="cartToggle" hidden <%= "open".equals(request.getAttribute("cartToggle")) ? "checked" : "" %> >

    <div id="cartSidebar" class="cart-sidebar">
        <label for="cartToggle" class="closebtn">&times;</label>
        <h2>Your Cart</h2>
        <div id="cartItems">
            <%  session = request.getSession();
                ArrayList<OrderItemInfo> cart = (ArrayList<OrderItemInfo>) session.getAttribute("cart");
                if (cart == null || cart.isEmpty()) { %>
                    <p class="empty-cart">Your cart is empty!</p>
                <% } else { %>
            <div class="cart-items-wrapper">
                <% for (OrderItemInfo itemInfo : cart) { %>
                <div class="cart-item-info">
                    <img src="resources/<%= itemInfo.getItem().getIsbn() %>.jpg" alt="<%= itemInfo.getItem().getIsbn() %>">
                    <div class="item-details">
                        <p class="item-title"><%= itemInfo.getItem().getTitle() %></p>
                        <p class="item-author">by <%= itemInfo.getItem().getAuthor() %></p>
                        <p class="item-price"><%= itemInfo.getItem().getPrice() %> kr</p>
                        <p class="item-quantity">Quantity: <%= itemInfo.getNrOfItems() %></p>
                    </div>
                    <form action="cart-servlet" method="POST" class="remove-item-form">
                        <input type="hidden" name="action" value="remove">
                        <input type="hidden" name="itemId" value="<%= itemInfo.getItem().getItemId() %>">
                        <button type="submit" class="remove-item-btn">X</button>
                    </form>
                </div>
                <% } %>
            </div>
            <% } %>
        </div>
        <% if (cart != null && !cart.isEmpty()) { %>
            <label class="proceedToPay">
                <% if (currentUser == null) {%>
                    <a href="login.jsp?redirect=orderSummary.jsp">Proceed to Pay</a>
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
        <%  Collection<BookInfo> books = (Collection<BookInfo>) request.getAttribute("booksInfo");
            Iterator<BookInfo> it = books.iterator();
            for (; it.hasNext();) {
                BookInfo b = it.next();
                int nrOfCopies = b.getNrOfCopies();
        %>
            <div class="shop-item">
                <div class="item-info">
                    <img src="resources/<%= b.getIsbn()%>.jpg" alt="<%= b.getIsbn()%>">
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
                        <input type="hidden" name="cartToggle" value="open">
                        <input type="hidden" name="action" value="add">
                        <button type="submit" class="add-to-cart-button" <%= (nrOfCopies == 0) ? "disabled" : "" %> >Add to Cart</button>
                    </form>
                </div>
            </div>
        <%}%>
    </div>
</body>
</html>
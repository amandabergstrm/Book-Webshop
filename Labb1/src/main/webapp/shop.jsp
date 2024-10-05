<%@ page import="businessObjects.UserHandler" %>
<%@ page import="ui.UserInfo" %>
<%@ page import="ui.BookInfo" %>
<%@ page import="businessObjects.BookHandler" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Collection" %>
<%@ page import="businessObjects.Authority" %>
<%@ page import="ui.OrderItemInfo" %>
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

    <div class="topnav">
        <a class="active" href="shop.jsp">Home</a>
        <% if(currentUser != null && currentUser.getAuthority() != Authority.Customer) { %>
            <a href="#news">Worker</a>
            <a href="products.jsp">Products</a>
        <% } %>
        <% if(currentUser != null && currentUser.getAuthority() == Authority.Admin) { %>
            <a href="users.jsp">Users</a>
        <% } %>
        <a class="cart-link"><label for="cartToggle" class="open-link">View Cart</label></a>
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
                    <a href="newOrder.jsp">Proceed to Pay</a>
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

<body>
</body>
</html>
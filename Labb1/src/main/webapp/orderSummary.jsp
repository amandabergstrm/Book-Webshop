<%@ page import="ui.OrderItemInfo" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/orderSummaryStyle.css">
    <title>New Order</title>
</head>
<body>
<div class="cart-container">
    <h2>Your Order Summary</h2>

    <% ArrayList<OrderItemInfo> cart = (ArrayList<OrderItemInfo>) session.getAttribute("cart");
        double total = 0.0;

        if (cart != null && !cart.isEmpty()) {
         for (OrderItemInfo item : cart) {
             total += item.getNrOfItems() * item.getItem().getPrice();
    %>
    <div class="cart-item">
        <div class="item-info">
            <img src="resources/<%= item.getItem().getTitle() %>.jpg" alt="<%= item.getItem().getTitle() %>" class="item-image">
            <div class="item-details">
                <p><span class="item-title"><%= item.getItem().getTitle() %></span> by <%= item.getItem().getAuthor() %></p>
                <p><%= item.getNrOfItems() %> x <%= item.getItem().getPrice() %> kr</p>
            </div>
        </div>
        <div class="item-price">
            <%= item.getNrOfItems() * item.getItem().getPrice() %> kr
        </div>
    </div>
     <% } }%>
    <div class="total">
        Total: <%= total %> kr
    </div>

 <!-- Confirm order form -->
    <form action="order-servlet" method="POST">
     <input type="hidden" name="action" value="confirmOrder">
     <button type="submit" class="confirm-btn">Confirm Order</button>
    </form>
</div>
</body>
</html>
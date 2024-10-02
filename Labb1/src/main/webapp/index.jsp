<%@ page import="businessObjects.UserHandler" %>
<%@ page import="ui.UserInfo" %>
<%@ page import="ui.BookInfo" %>
<%@ page import="businessObjects.BookHandler" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Collection" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="mainPageStyle.css">
    <link rel="stylesheet" href="shopItemsStyle.css">
    <link rel="stylesheet" href="navBarStyle.css">
    <link rel="stylesheet" href="sidebarCartStyle.css">
    <link rel="stylesheet" href="logInStyle.css">
</head>

<body>
    <div class="topnav">
        <a class="active" href="#home">Home</a>
        <a href="#news">Idk</a>
        <a href="#login">Login</a>
        <a class="cart-link"><label for="cartToggle" class="open-link">View Cart</label></a>
    </div>

    <input type="checkbox" id="cartToggle" hidden>
    <div id="cartSidebar" class="cart-sidebar">
        <label for="cartToggle" class="closebtn">&times;</label>
        <h2>Your Cart</h2>
        <div id="cartItems">
            <p>Book Title 1 - 199 kr</p>
            <p>Book Title 2 - 249 kr</p>
        </div>
        <button><label for="dialogToggle" class="login-button">Proceed to Pay</label></button>
        <input type="checkbox" id="dialogToggle" hidden>

        <!-- Login/Create User Dialog Box -->
        <div class="login-dialog">
            <div class="login-content">
                <label for="dialogToggle" class="close-btn">&times;</label>
                <h2>You have to be logged in to shop!</h2>
                <div class="choice-buttons">
                    <label for="loginToggle" class="option-button">Log In</label>
                    <label for="createUserToggle" class="option-button">Create User</label>
                </div>
            </div>
        </div>

        <input type="checkbox" id="loginToggle" hidden>
        <input type="checkbox" id="createUserToggle" hidden>

        <!-- Log In Form -->
        <div class="form-dialog" id="loginForm">
            <div class="form-content">
                <label for="loginToggle" class="close-btn">&times;</label>
                <h2>Log In</h2>
                <form action="user-servlet" method="POST">
                    <input type="hidden" name="action" value="login">

                    <label for="loginEmail">Email:</label>
                    <input type="email" id="loginEmail" name="email" required>

                    <label for="loginPassword">Password:</label>
                    <input type="password" id="loginPassword" name="password" required>

                    <button class="option-button" type="submit">Log In</button>
                </form>
            </div>
        </div>

        <!-- Create User Form -->
        <div class="form-dialog" id="createUserForm">
            <div class="form-content">
                <label for="createUserToggle" class="close-btn">&times;</label>
                <h2>Create User</h2>
                <form action="user-servlet">
                    <input type="hidden" name="action" value="createUser">

                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" required>

                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" required>

                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required>

                    <button type="submit">Create Account</button>
                </form>
            </div>
        </div>
    </div>

    <div class="shopTitle">
        <h1><%= "The Bookshop" %></h1>
    </div>

    <div class="shop-container">
        <%
            Collection<BookInfo> books = BookHandler.getAllBooks(); //ska hämta från kontrollern istället
            Iterator<BookInfo> it = books.iterator();
            for (; it.hasNext();) {
                BookInfo b = it.next();
        %>
            <div class="shop-item">
                <div class="item-info">
                    <img src="resources/<%= b.getTitle()%>.jpg" alt="<%= b.getTitle()%>"> <!--img samma med bild om dem lagras-->
                    <h3><%= b.getTitle()%></h3>
                    <p><%= b.getAuthor()%></p>
                    <isbn>ISBN: <%= b.getIsbn()%></isbn>
                    <p><%= b.getPrice()%> kr</p>
                    <button>Add to Cart</button>
                </div>
            </div>
        <%}%>
    </div>
</body>

<body>
<br/>
<a href="hello-servlet">Hello Servlet</a>
</body>
</html>
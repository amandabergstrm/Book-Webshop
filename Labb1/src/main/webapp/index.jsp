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
</head>

<body>
    <div class="topnav">
        <a class="active" href="#home">Home</a>
        <a href="#news">Idk</a>
        <a href="#contact">View cart</a>
        <a href="#login">Login</a>
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
<% UserInfo user = UserHandler.getUserByEmail("poriazov@kth.se");%>
<%= user.getName() %>

<% BookInfo book = BookHandler.getBookByISBN("9781451690316");%>
<%= book.getTitle() %>
</body>
</html>
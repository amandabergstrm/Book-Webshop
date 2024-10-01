<%@ page import="businessObjects.UserHandler" %>
<%@ page import="ui.UserInfo" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello Betty!" %>
</h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>
<% UserInfo user = UserHandler.getUserByEmail("poriazov@kth.se");%>
<% user.getName(); %>
</body>
</html>
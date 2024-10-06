<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/logInStyle.css">
    <title>Login or Create User</title>
</head>
<body>

<input type="radio" id="login" name="toggleForm" checked>
<input type="radio" id="createUser" name="toggleForm">

<div class="form-container">
    <!-- Log In Form -->
    <div id="loginForm">
        <h2>Log In</h2>
        <form action="login-servlet" method="POST">
            <input type="hidden" name="action" value="login">
            <input type="hidden" name="redirect" value="<%= request.getParameter("redirect") != null ? request.getParameter("redirect") : "" %>">

            <label for="loginEmail">Email:</label>
            <input type="email" id="loginEmail" name="email" required>

            <label for="loginPassword">Password:</label>
            <input type="password" id="loginPassword" name="password" required>

            <button type="submit">Log In</button>
        </form>
        <label for="createUser" class="toggle-link">Create User</label>
    </div>

    <!-- Create User Form -->
    <div id="createUserForm">
        <h2>Create User</h2>
        <form action="login-servlet" method="POST">
            <input type="hidden" name="action" value="createUser">
            <input type="hidden" name="redirect" value="<%= request.getParameter("redirect") != null ? request.getParameter("redirect") : "" %>">

            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>

            <button type="submit">Create Account</button>
        </form>
        <label for="login" class="toggle-link">Log In</label>
    </div>
</div>
</body>
</html>
<%@ page import="ui.UserInfo" %>
<%@ page import="businessObjects.Authority" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/navBarStyle.css">
    <link rel="stylesheet" href="css/userStyle.css">
    <link rel="stylesheet" href="css/handleBookForms.css">
    <title>Manage users</title>
</head>
<body>
    <% UserInfo currentUser = (UserInfo) session.getAttribute("currentUser"); %>

    <div class="topnav">
        <a class="active" href="shop-servlet">Home</a>
        <a href="orders.jsp">Orders</a>
        <a href="product-servlet">Products</a>
        <a href="user-servlet">Users</a>
        <a href="profile.jsp">Profile</a>
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

    <div class="users-list">
        <div class="list-user">
            <div class="user-info">
                <a>Name</a>
                <a>Email</a>
                <a>Password</a>
                <a>Authority</a>

                <!-- Create New User Button-->
                <input type="checkbox" id="createUserToggle" hidden>
                <div class="form-container" id="createUserForm">
                    <label for="createUserToggle" class="close-btn">&times;</label>
                    <h2>Add New User</h2>
                    <form action="user-servlet" method="POST">
                        <input type="hidden" name="action" value="create">
                        <label for="username">Username:</label>
                        <input type="text" id="username" name="username" required>
                        <label for="email">Email:</label>
                        <input type="email" id="email" name="email" required>
                        <label for="password">Password:</label>
                        <input type="password" id="password" name="password" required>
                        <label for="authority">Authority:</label>
                        <select id="authority" name="authority" required>
                            <% for (Authority authority : Authority.values()) { %>
                            <option value="<%= authority %>"><%= authority %></option>
                            <% } %>
                        </select>
                        <button type="submit">Create Account</button>
                    </form>
                </div>
                    <label for="createUserToggle" class="toggle-link">Add</label>
            </div>
        </div>

        <%  Collection<UserInfo> usersInfo = (Collection<UserInfo>) request.getAttribute("usersInfo");
            Iterator<UserInfo> it = usersInfo.iterator();
            for (; it.hasNext();) {
                UserInfo userInfo = it.next();
                String id = userInfo.getEmail();
        %>

        <div class="list-user">
            <div class="user-info">
                <p><%= userInfo.getName() %></p>
                <p><%= userInfo.getEmail() %></p>
                <p>*************</p>
                <p><%= userInfo.getAuthority() %></p>

                <!-- Edit Button -->
                <input type="checkbox" id="editUserToggle<%=id%>" hidden>
                <div class="form-container" id="editUserForm<%=id%>">
                    <label for="editUserToggle<%=id%>" class="close-btn">&times;</label>
                    <h2>Edit user: <%=id%></h2>
                    <form action="user-servlet" method="POST">
                        <input type="hidden" name="action" value="edit">
                        <input type="hidden" name="email" value="<%=id%>">
                        <label for="newAuthority<%=id%>">New Authority (leave empty to keep unchanged):</label>
                        <select id="newAuthority<%=id%>" name="authority" required>
                            <% for (Authority authority : Authority.values()) { %>
                            <option value="<%= authority %>"><%= authority %></option>
                            <% } %>
                        </select>
                        <button type="submit">Save Changes</button>
                    </form>
                </div>
                <label for="editUserToggle<%=id%>" class="toggle-link">Edit</label>

                <!-- Delete Button -->
                <form action="user-servlet" method="POST" onsubmit="return confirm('Are you sure you want to remove this user?');">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="email" value="<%= id %>">
                    <button type="submit" class="delete-btn">Delete</button>
                </form>
            </div>
        </div>
        <%}%>
    </div>
</body>
</html>

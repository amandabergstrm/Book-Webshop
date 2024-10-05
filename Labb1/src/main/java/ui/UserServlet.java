package ui;

import java.io.*;
import java.util.Collection;
import businessObjects.Authority;
import businessObjects.BookHandler;
import businessObjects.Genre;
import businessObjects.UserHandler;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "userServlet", value = "/user-servlet")
public class UserServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        if ("create".equals(action)) {
            createUser(request, response);
        } else if ("edit".equals(action)) {
            editUser(request, response);
        } else if ("delete".equals(action)) {
            deleteUser(request, response);
        }
    }

    private void createUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String authority = request.getParameter("authority");

        UserHandler.createUser(new UserInfo(Authority.valueOf(authority), name, email, password));
        updateSession(request, response);
        response.sendRedirect("users.jsp");
    }

    private void editUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String authorityStr = request.getParameter("authority");
        UserInfo userInfo = UserHandler.getUserByEmail(email);
        assert userInfo != null;
        userInfo.setAuthority(Authority.valueOf(authorityStr));
        UserHandler.updateUser(userInfo);
        updateSession(request, response);
        response.sendRedirect("users.jsp");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        UserHandler.deleteUserByEmail(email);
        updateSession(request, response);
        response.sendRedirect("users.jsp");
    }

    private void updateSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Collection<UserInfo> usersInfo = UserHandler.getAllUsers();
        session.setAttribute("usersInfo", usersInfo);
    }
}
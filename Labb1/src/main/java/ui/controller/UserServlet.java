package ui.controller;

import java.io.*;
import java.util.Collection;
import businessObjects.Authority;
import businessObjects.UserHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import ui.view.UserInfo;

@WebServlet(name = "userServlet", value = "/user-servlet")
public class UserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        updateAndRedirect(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        if ("create".equals(action)) {
            createUser(request);
        } else if ("edit".equals(action)) {
            editUser(request);
        } else if ("delete".equals(action)) {
            deleteUser(request);
        }
        updateAndRedirect(request, response);
    }

    private void createUser(HttpServletRequest request) {
        String name = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String authority = request.getParameter("authority");
        UserHandler.createUser(new UserInfo(Authority.valueOf(authority), name, email, password));
    }

    private void editUser(HttpServletRequest request) {
        String email = request.getParameter("email");
        String authorityStr = request.getParameter("authority");
        UserInfo userInfo = UserHandler.getUserByEmail(email);
        assert userInfo != null;
        userInfo.setAuthority(Authority.valueOf(authorityStr));
        UserHandler.updateUser(userInfo);
    }

    private void deleteUser(HttpServletRequest request) {
        String email = request.getParameter("email");
        UserHandler.deleteUserByEmail(email);
    }

    private void updateAndRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Collection<UserInfo> usersInfo = UserHandler.getAllUsers();
        request.setAttribute("usersInfo", usersInfo);
        request.getRequestDispatcher("users.jsp").forward(request, response);
    }
}
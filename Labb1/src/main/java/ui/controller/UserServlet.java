package ui.controller;

import java.io.*;
import java.util.Collection;
import businessObjects.Authority;
import businessObjects.UserHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import ui.view.UserInfo;

/**
 * The {@code UserServlet} class handles operations related to users such as creating, editing,
 * and deleting users, and displaying all users.
 */
@WebServlet(name = "userServlet", value = "/user-servlet")
public class UserServlet extends HttpServlet {

    /**
     * Handles GET requests to retrieve and display all users.
     *
     * @param request  the {@link HttpServletRequest} containing the request data
     * @param response the {@link HttpServletResponse} used to forward the user to the user management page
     * @throws IOException      if an input or output error is detected
     * @throws ServletException if the servlet encounters a processing error
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        updateAndRedirect(request, response);
    }

    /**
     * Handles POST requests to create, edit, or delete users based on the provided action.
     *
     * @param request  the {@link HttpServletRequest} containing the action and user data
     * @param response the {@link HttpServletResponse} used to forward the user after processing the action
     * @throws IOException      if an input or output error is detected
     * @throws ServletException if the servlet encounters a processing error
     */
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

    /**
     * Creates a new user based on the form data provided in the request.
     *
     * @param request the {@link HttpServletRequest} containing the new user data
     */
    private void createUser(HttpServletRequest request) {
        String name = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String authority = request.getParameter("authority");
        UserHandler.createUser(new UserInfo(Authority.valueOf(authority), name, email, password));
    }

    /**
     * Edits an existing user's authority based on the form data provided in the request.
     *
     * @param request the {@link HttpServletRequest} containing the user data to be updated
     */
    private void editUser(HttpServletRequest request) {
        String email = request.getParameter("email");
        String authorityStr = request.getParameter("authority");
        UserInfo userInfo = UserHandler.getUserByEmail(email);
        assert userInfo != null;
        userInfo.setAuthority(Authority.valueOf(authorityStr));
        UserHandler.updateUser(userInfo);
    }

    /**
     * Deletes a user by their email.
     *
     * @param request the {@link HttpServletRequest} containing the email of the user to be deleted
     */
    private void deleteUser(HttpServletRequest request) {
        String email = request.getParameter("email");
        UserHandler.deleteUserByEmail(email);
    }

    /**
     * Retrieves all users and forwards the request to the users management page.
     *
     * @param request  the {@link HttpServletRequest} containing the request data
     * @param response the {@link HttpServletResponse} used to forward the user to the users page
     * @throws IOException      if an input or output error is detected
     * @throws ServletException if the servlet encounters a processing error
     */
    private void updateAndRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Collection<UserInfo> usersInfo = UserHandler.getAllUsers();
        request.setAttribute("usersInfo", usersInfo);
        request.getRequestDispatcher("users.jsp").forward(request, response);
    }
}
package ui.controller;

import java.io.*;
import businessObjects.Authority;
import businessObjects.UserHandler;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import ui.view.UserInfo;

/**
 * The {@code LoginServlet} class manages user login, account creation, and logout functionalities.
 * It processes login, user creation, and logout requests, redirecting users to appropriate pages.
 */
@WebServlet(name = "loginServlet", value = "/login-servlet")
public class LoginServlet extends HttpServlet {

    /**
     * Handles login, user creation, and logout based on the action parameter.
     *
     * @param request  the {@link HttpServletRequest} containing login, account creation, or logout data
     * @param response the {@link HttpServletResponse} used to redirect the user after processing
     * @throws IOException if an input or output error is detected
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        if ("login".equals(action)) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String redirect = request.getParameter("redirect");

            UserInfo existingUser = UserHandler.getUserByEmail(email);
            if (existingUser == null) {
                System.out.println("User not found, try again or create");
                response.sendRedirect("login.jsp");
            } else if (existingUser.getPassword().equals(password)) {
                System.out.println("Logged in as " + existingUser.getName());
                session.setAttribute("currentUser", existingUser);

                if (redirect != null && !redirect.isEmpty()) {
                    response.sendRedirect(redirect);
                } else {
                    response.sendRedirect("shop-servlet");
                }
            } else {
                System.out.println("Wrong password (correct email)");
                response.sendRedirect("login.jsp");
            }
        } else if ("createUser".equals(action)) {
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String redirect = request.getParameter("redirect");

            UserInfo existingUser = UserHandler.getUserByEmail(email);
            if (existingUser != null) {
                System.out.println("Account already exists, please try again");
                response.sendRedirect("login.jsp");
            } else {
                UserInfo newUserInfo = new UserInfo(Authority.Customer, username, email, password);
                UserHandler.createUser(newUserInfo);
                System.out.println("Proceed to checkout");
                session.setAttribute("currentUser", newUserInfo);
                if (redirect != null && !redirect.isEmpty()) {
                    response.sendRedirect(redirect);
                } else {
                    response.sendRedirect("shop-servlet");
                }
            }
        } else if ("logout".equals(action)) {
            session.setAttribute("currentUser", null);
            session.invalidate();
            response.sendRedirect("shop-servlet");
        } else {
            response.getWriter().write("Invalid action.");
        }
    }
}
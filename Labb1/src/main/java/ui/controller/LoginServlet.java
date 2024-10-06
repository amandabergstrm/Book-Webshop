package ui.controller;

import java.io.*;
import businessObjects.Authority;
import businessObjects.UserHandler;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import ui.view.UserInfo;

@WebServlet(name = "loginServlet", value = "/login-servlet")
public class LoginServlet extends HttpServlet {
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
                System.out.println("User hittades inte, prova igen eller create");
                response.sendRedirect("login.jsp");
            } else if (existingUser.getPassword().equals(password)) {
                System.out.println("logged in as" + existingUser.getName());
                session.setAttribute("currentUser", existingUser);

                if (redirect != null && !redirect.isEmpty()) {
                    response.sendRedirect(redirect);
                } else {
                    response.sendRedirect("shop-servlet");
                }
            } else {
                System.out.println("Fel lösenord (rätt email)");
                response.sendRedirect("login.jsp");
            }
        } else if ("createUser".equals(action)) {
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String redirect = request.getParameter("redirect");

            UserInfo existingUser = UserHandler.getUserByEmail(email);
            if (existingUser != null) {
                System.out.println("Skriv att kontot redan finns så får dem försöka igen");
                response.sendRedirect("login.jsp");
            } else {
                UserInfo newUserInfo = new UserInfo(Authority.Customer, username, email, password);
                UserHandler.createUser(newUserInfo);
                System.out.println("Fortsätt till kassa");
                session.setAttribute("currentUser", newUserInfo);
                if (redirect != null && !redirect.isEmpty()) {
                    response.sendRedirect(redirect);
                } else {
                    response.sendRedirect("shop-servlet");
                }
            }
        } else if ("logout".equals(action)) {
            session = request.getSession();
            session.setAttribute("currentUser", null);
            session.invalidate();
            response.sendRedirect("shop-servlet");
        } else {
            response.getWriter().write("Invalid action.");
        }
    }
}
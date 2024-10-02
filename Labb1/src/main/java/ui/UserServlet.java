package ui;

import java.io.*;

import businessObjects.Authority;
import businessObjects.UserHandler;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "userServlet", value = "/user-servlet")
public class UserServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        if ("login".equals(action)) {
            UserInfo existingUser = UserHandler.getUserByEmail(request.getParameter("loginEmail"));
            if (existingUser == null) {
                System.out.println("Skriv att user ej finns och öppna create user dialog");
            } else {
                System.out.println("Fortsätt till kassa");
            }
        } else if ("createUser".equals(action)) {
            UserInfo existingUser = UserHandler.getUserByEmail(request.getParameter("email"));
            if (existingUser != null) {
                System.out.println("Skriv att kontot redan finns så får dem försöka igen");
            }
            UserHandler.createUser(new UserInfo(Authority.Customer, request.getParameter("username"), request.getParameter("email"), request.getParameter("password")));
            System.out.println("Fortsätt till kassa");
        } else {
            response.getWriter().write("Invalid action.");
        }
    }
}
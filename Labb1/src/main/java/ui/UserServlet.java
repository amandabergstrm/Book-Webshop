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

        // kolla om den finns
        if ("login".equals(action)) {

        } else if ("createUser".equals(action)) {
            UserHandler.createUser(new UserInfo(Authority.Customer, request.getParameter("username"), request.getParameter("email"), request.getParameter("password")));
        } else {
            response.getWriter().write("Invalid action.");
        }
    }
}

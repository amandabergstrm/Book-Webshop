package ui.controller;

import businessObjects.OrderHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ui.view.OrderInfo;
import ui.view.UserInfo;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "profileServlet", value = "/profile-servlet")
public class ProfileServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserInfo user = (UserInfo) request.getSession().getAttribute("currentUser");
        ArrayList<OrderInfo> userOrders = OrderHandler.getUserOrders(user.getEmail());
        request.setAttribute("userOrders", userOrders);
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }
}
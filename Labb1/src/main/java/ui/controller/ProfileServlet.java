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

/**
 * The {@code ProfileServlet} class handles displaying the user's profile and orders.
 * It retrieves the user's orders and forwards the user to their profile page.
 */
@WebServlet(name = "profileServlet", value = "/profile-servlet")
public class ProfileServlet extends HttpServlet {

    /**
     * Handles GET requests to retrieve the user's orders and display their profile.
     *
     * @param request  the {@link HttpServletRequest} containing the request data
     * @param response the {@link HttpServletResponse} used to forward the user to their profile page
     * @throws IOException      if an input or output error is detected
     * @throws ServletException if the servlet encounters a processing error
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserInfo user = (UserInfo) request.getSession().getAttribute("currentUser");
        ArrayList<OrderInfo> userOrders = OrderHandler.getUserOrders(user.getEmail());
        request.setAttribute("userOrders", userOrders);
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }
}
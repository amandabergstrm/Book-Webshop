package ui;

import businessObjects.BookHandler;
import businessObjects.OrderHandler;
import businessObjects.UserHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@WebServlet(name = "orderServlet", value = "/order-servlet")
public class OrderServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        ArrayList<OrderInfo> ordersInfo = OrderHandler.getAllOrders();
        session.setAttribute("ordersInfo", ordersInfo);
        System.out.println(ordersInfo.toString());

        request.getRequestDispatcher("orders.jsp").forward(request, response);
    }

    private void updateSession(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        ArrayList<OrderInfo> ordersInfo = OrderHandler.getAllOrders();
        session.setAttribute("ordersInfo", ordersInfo);
    }
}

package ui;

import businessObjects.Authority;
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

@WebServlet(name = "orderServlet", value = "/orders-servlet")
public class OrderServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        updateAndRedirect(request, response);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        if ("edit".equals(action)) {
            updateOrderStatus(request);
        }
        updateAndRedirect(request, response);
    }

    private void updateOrderStatus(HttpServletRequest request) {
        int orderNr = Integer.parseInt(request.getParameter("orderNr"));
        String orderStatus = request.getParameter("status");
        OrderHandler.updateOrderStatus(orderNr, orderStatus);
    }
    private void updateAndRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ArrayList<OrderInfo> ordersInfo = OrderHandler.getAllOrders();
        request.setAttribute("ordersInfo", ordersInfo);
        request.getRequestDispatcher("orders.jsp").forward(request, response);
    }
}

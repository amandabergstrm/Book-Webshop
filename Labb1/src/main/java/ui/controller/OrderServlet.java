package ui.controller;

import businessObjects.OrderHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * The {@code OrderServlet} class manages order-related operations in the system.
 * It handles updating order statuses and retrieving all orders for display.
 */
@WebServlet(name = "orderServlet", value = "/orders-servlet")
public class OrderServlet extends HttpServlet {

    /**
     * Handles GET requests to retrieve and display all orders.
     *
     * @param request  the {@link HttpServletRequest} containing the request data
     * @param response the {@link HttpServletResponse} used to forward the user to the order display page
     * @throws IOException      if an input or output error is detected
     * @throws ServletException if the servlet encounters a processing error
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        updateAndRedirect(request, response);
    }

    /**
     * Handles POST requests to update order statuses and then retrieve all orders for display.
     *
     * @param request  the {@link HttpServletRequest} containing the request data
     * @param response the {@link HttpServletResponse} used to forward the user to the order display page
     * @throws IOException      if an input or output error is detected
     * @throws ServletException if the servlet encounters a processing error
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        if ("edit".equals(action)) {
            updateOrderStatus(request);
        }
        updateAndRedirect(request, response);
    }

    /**
     * Updates the status of an order based on the request parameters.
     *
     * @param request the {@link HttpServletRequest} containing the order number and new status
     */
    private void updateOrderStatus(HttpServletRequest request) {
        int orderNr = Integer.parseInt(request.getParameter("orderNr"));
        String orderStatus = request.getParameter("status");
        OrderHandler.updateOrderStatus(orderNr, orderStatus);
    }

    /**
     * Retrieves all orders and forwards the user to the orders page for display.
     *
     * @param request  the {@link HttpServletRequest} containing the request data
     * @param response the {@link HttpServletResponse} used to forward the user to the order display page
     * @throws IOException      if an input or output error is detected
     * @throws ServletException if the servlet encounters a processing error
     */
    private void updateAndRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Collection ordersInfo = OrderHandler.getAllOrders();
        request.setAttribute("ordersInfo", ordersInfo);
        request.getRequestDispatcher("orders.jsp").forward(request, response);
    }
}
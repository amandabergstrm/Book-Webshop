package ui.controller;

import businessObjects.BookHandler;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ui.view.BookInfo;
import ui.view.OrderItemInfo;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The {@code CartServlet} class manages cart-related operations in an online shop.
 * It handles adding items to the cart, removing items, and updating the session.
 */
@WebServlet(name = "cartServlet", value = "/cart-servlet")
public class CartServlet extends HttpServlet {

    /**
     * Handles adding and removing items from the cart based on the action specified.
     *
     * @param request  the {@link HttpServletRequest} containing the action and item information
     * @param response the {@link HttpServletResponse} used to redirect the user after the operation
     * @throws IOException if an input or output error is detected
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String itemIdStr = request.getParameter("itemId");
        String action = request.getParameter("action");
        ArrayList<OrderItemInfo> cart = (ArrayList<OrderItemInfo>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        if ("add".equals(action)) {
            BookInfo bookInfo = BookHandler.getBookByItemId(Integer.parseInt(itemIdStr));
            OrderItemInfo orderItemInfo = new OrderItemInfo(bookInfo, bookInfo.getItemId(), 1);
            boolean existingItem = false;

            for (OrderItemInfo item : cart) {
                if (item.getItemId() == orderItemInfo.getItemId()) {
                    if (item.getItem().getNrOfCopies() != 1) {
                        item.setNrOfItems(item.getNrOfItems() + 1);
                        item.getItem().setNrOfCopies(item.getItem().getNrOfCopies() - 1);
                    }
                    existingItem = true;
                    break;
                }
            }
            if (!existingItem) {
                if (orderItemInfo.getItem().getNrOfCopies() != 1)
                    cart.add(orderItemInfo);
            }
        } else if ("remove".equals(action)) {
            int itemId = Integer.parseInt(itemIdStr);
            cart.removeIf(item -> item.getItemId() == itemId);
        }

        String cartToggle = request.getParameter("cartToggle");
        response.sendRedirect("shop-servlet?cartToggle=" + cartToggle);
    }
}
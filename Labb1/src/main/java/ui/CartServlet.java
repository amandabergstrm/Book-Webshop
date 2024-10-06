package ui;

import businessObjects.BookHandler;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@WebServlet(name = "cartServlet", value = "/cart-servlet")
public class CartServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        String itemIdStr = request.getParameter("itemId");
        ArrayList<OrderItemInfo> cart = (ArrayList<OrderItemInfo>) session.getAttribute("cart");

        BookInfo bookInfo = BookHandler.getBookByItemId(Integer.parseInt(itemIdStr));
        OrderItemInfo orderItemInfo = new OrderItemInfo(bookInfo, bookInfo.getItemId(), 1);
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

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

        String cartToggle = request.getParameter("cartToggle");
        response.sendRedirect("shop-servlet?cartToggle=" + cartToggle);
    }
}
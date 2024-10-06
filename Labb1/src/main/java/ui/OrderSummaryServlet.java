package ui;

import businessObjects.BookHandler;
import businessObjects.OrderHandler;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@WebServlet(name = "orderSummaryServlet", value = "/order-summary-servlet")
public class OrderSummaryServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        if ("confirmOrder".equals(action)) {
            HttpSession session = request.getSession();
            ArrayList<OrderItemInfo> cart = (ArrayList<OrderItemInfo>) session.getAttribute("cart");
            UserInfo user = (UserInfo) session.getAttribute("currentUser");

            if (cart != null && !cart.isEmpty()) {
                ArrayList<OrderItemInfo> orderItems = new ArrayList<>();
                for (OrderItemInfo item : cart) {
                    orderItems.add(new OrderItemInfo(item.getItemId(), item.getNrOfItems()));
                }

                OrderInfo orderInfo = new OrderInfo(user.getEmail(), orderItems);
                OrderHandler.createOrder(orderInfo);
                updateBookStock(orderItems, session);

                session.removeAttribute("cart");
                response.sendRedirect("profile-servlet");
            } else {
                response.sendRedirect("shop-servlet");
            }
        }
    }

    private void updateBookStock(ArrayList<OrderItemInfo> orderItems, HttpSession session) {
        for (OrderItemInfo orderItem : orderItems) {
            BookInfo book = BookHandler.getBookByItemId(orderItem.getItemId());
            int updatedNrOfCopies = book.getNrOfCopies() - orderItem.getNrOfItems();
            if (updatedNrOfCopies >= 0) { // Ensure stock doesn't go below 0
                BookHandler.updateBook(book, updatedNrOfCopies, book.getPrice());
            }
        }

        Collection<BookInfo> booksInfo = BookHandler.getAllBooks();
        session.setAttribute("booksInfo", booksInfo);
    }
}
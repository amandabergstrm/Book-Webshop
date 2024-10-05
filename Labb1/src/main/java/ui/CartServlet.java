package ui;

import businessObjects.BookHandler;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "cartServlet", value = "/cart-servlet")
public class CartServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        String itemIdStr = request.getParameter("itemId");
        BookInfo book = BookHandler.getBookByItemId(Integer.parseInt(itemIdStr));

        ArrayList<BookInfo> cart = (ArrayList<BookInfo>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        cart.add(book);
        response.sendRedirect("shop.jsp");
    }
}
package ui;

import businessObjects.BookHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@WebServlet(name = "shopServlet", value = "/shop-servlet")
public class ShopServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Collection<BookInfo> booksInfo = BookHandler.getAllBooks();
        request.setAttribute("booksInfo", booksInfo);

        ArrayList<String> genres = BookHandler.getAllCategories();
        request.setAttribute("genres", genres);

        String cartToggle = request.getParameter("cartToggle");
        request.setAttribute("cartToggle", cartToggle);

        request.getRequestDispatcher("shop.jsp").forward(request, response);
    }
}
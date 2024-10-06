package ui.controller;

import businessObjects.BookHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ui.view.BookInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The {@code ShopServlet} class is responsible for handling requests related to the shop.
 * It retrieves all books and categories, and forwards the user to the shop page.
 */
@WebServlet(name = "shopServlet", value = "/shop-servlet")
public class ShopServlet extends HttpServlet {

    /**
     * Handles GET requests to display the shop page with all books and categories.
     *
     * @param request  the {@link HttpServletRequest} containing request data
     * @param response the {@link HttpServletResponse} used to forward the user to the shop page
     * @throws IOException      if an input or output error is detected
     * @throws ServletException if the servlet encounters a processing error
     */
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
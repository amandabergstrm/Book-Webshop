package ui;

import businessObjects.BookHandler;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@WebServlet("/shop-servlet")
public class ShopServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Fetch all books
        Collection<BookInfo> booksInfo = BookHandler.getAllBooks();
        request.setAttribute("books", booksInfo);

        // Forward the request to index.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }

    private void getShopItems(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ArrayList<BookInfo> booksInfo = (ArrayList<BookInfo>) BookHandler.getAllBooks();
        request.setAttribute("books", booksInfo);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    /*
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        ArrayList<BookInfo> booksInfo = (ArrayList<BookInfo>) BookHandler.getAllBooks();
        session.setAttribute("books", booksInfo);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }*/
}

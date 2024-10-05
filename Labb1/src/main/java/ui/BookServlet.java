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

@WebServlet(name = "bookServlet", value = "/book-servlet")
public class BookServlet extends HttpServlet {
   @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        if ("category".equals(action)) {
            addCategory(request, response);
        } else if ("create".equals(action)) {
            createBook(request, response);
        } else if ("edit".equals(action)) {
            editBook(request, response);
        } else if ("delete".equals(action)) {
            deleteBook(request, response);
        }
    }

    private void addCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String genre = request.getParameter("newGenre");

        HttpSession session = request.getSession();
        ArrayList<String> genres = (ArrayList<String>) session.getAttribute("genres");
        if (genres != null && genres.contains(genre)) {
            request.setAttribute("errorMessage", "Genre already exists.");
        } else {
            if (genres == null) {
                genres = new ArrayList<>();
            }
            genres.add(genre);
            BookHandler.addCategory(genre);
           // session.setAttribute("genres", genres);
        }

        updateSession(request, response);
        response.sendRedirect("products.jsp");
    }

    private void createBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
       String isbn = request.getParameter("isbn");
       String title = request.getParameter("title");
       String author = request.getParameter("author");
       String genre = request.getParameter("genre");
       int price = Integer.parseInt(request.getParameter("price"));
       int nrOfCopies = Integer.parseInt(request.getParameter("nrOfCopies"));

       BookHandler.createBook(new BookInfo(isbn, title, genre, author, price, nrOfCopies));
       updateSession(request, response);
       response.sendRedirect("products.jsp");
    }

    private void editBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String itemIdStr = request.getParameter("itemId");
        String priceStr = request.getParameter("price");
        String nrOfCopiesStr = request.getParameter("nrOfCopies");
        int itemId = Integer.parseInt(itemIdStr);
        BookInfo book = BookHandler.getBookByItemId(itemId);
        int newPrice = book.getPrice();
        int newNrOfCopies = book.getNrOfCopies();

        if (priceStr != null && !priceStr.isEmpty()) {
            newPrice = Integer.parseInt(priceStr);
        }

        if (nrOfCopiesStr != null && !nrOfCopiesStr.isEmpty()) {
            newNrOfCopies = Integer.parseInt(nrOfCopiesStr);
        }

        BookHandler.updateBook(book, newNrOfCopies, newPrice);
        updateSession(request, response);
        response.sendRedirect("products.jsp");
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String itemIdStr = request.getParameter("itemId");
        BookHandler.deleteBookById(Integer.parseInt(itemIdStr));
        updateSession(request, response);
        response.sendRedirect("products.jsp");
    }

    private void updateSession(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Collection<BookInfo> booksInfo = BookHandler.getAllBooks();
        session.setAttribute("booksInfo", booksInfo);
    }
}
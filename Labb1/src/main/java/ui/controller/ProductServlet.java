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
@WebServlet(name = "productServlet", value = "/product-servlet")
public class ProductServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        updateAndRedirect(request, response);
    }

   @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        if ("category".equals(action)) {
            addCategory(request);
        } else if ("create".equals(action)) {
            createBook(request);
        } else if ("edit".equals(action)) {
            editBook(request);
        } else if ("delete".equals(action)) {
            deleteBook(request);
        }
       updateAndRedirect(request, response);
   }

    private void addCategory(HttpServletRequest request) {
        String genre = request.getParameter("newGenre");

        ArrayList<String> genres = (ArrayList<String>) request.getAttribute("genres");
        if (genres != null && genres.contains(genre)) {
            request.setAttribute("errorMessage", "Genre already exists.");
        } else {
            if (genres == null) {
                genres = new ArrayList<>();
            }
            genres.add(genre);
            BookHandler.addCategory(genre);
        }
    }

    private void createBook(HttpServletRequest request) {
       String isbn = request.getParameter("isbn");
       String title = request.getParameter("title");
       String author = request.getParameter("author");
       String genre = request.getParameter("genre");
       int price = Integer.parseInt(request.getParameter("price"));
       int nrOfCopies = Integer.parseInt(request.getParameter("nrOfCopies"));
       BookHandler.createBook(new BookInfo(isbn, title, genre, author, price, nrOfCopies));
    }

    private void editBook(HttpServletRequest request) {
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
    }

    private void deleteBook(HttpServletRequest request) {
        String itemIdStr = request.getParameter("itemId");
        BookHandler.deleteBookById(Integer.parseInt(itemIdStr));
    }

    private void updateAndRedirect(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Collection<BookInfo> booksInfo = BookHandler.getAllBooks();
        request.setAttribute("booksInfo", booksInfo);
        ArrayList<String> genres = BookHandler.getAllCategories();
        request.setAttribute("genres", genres);
        request.getRequestDispatcher("products.jsp").forward(request, response);
    }
}
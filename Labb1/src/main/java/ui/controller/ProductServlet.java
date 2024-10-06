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
 * The {@code ProductServlet} class manages product-related operations, such as adding, editing, and deleting books,
 * as well as managing categories.
 */
@WebServlet(name = "productServlet", value = "/product-servlet")
public class ProductServlet extends HttpServlet {

    /**
     * Handles GET requests for retrieving and displaying all products and categories.
     *
     * @param request  the {@link HttpServletRequest} containing the request data
     * @param response the {@link HttpServletResponse} used to forward the user to the product page
     * @throws IOException      if an input or output error is detected
     * @throws ServletException if the servlet encounters a processing error
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        updateAndRedirect(request, response);
    }

    /**
     * Handles POST requests for adding categories, creating, editing, and deleting books.
     *
     * @param request  the {@link HttpServletRequest} containing product-related data
     * @param response the {@link HttpServletResponse} used to forward the user after processing
     * @throws IOException      if an input or output error is detected
     * @throws ServletException if the servlet encounters a processing error
     */
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

    /**
     * Adds a new category to the system.
     *
     * @param request the {@link HttpServletRequest} containing the new category data
     */
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

    /**
     * Creates a new book based on the request parameters.
     *
     * @param request the {@link HttpServletRequest} containing the new book data
     */
    private void createBook(HttpServletRequest request) {
        String isbn = request.getParameter("isbn");
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String genre = request.getParameter("genre");
        int price = Integer.parseInt(request.getParameter("price"));
        int nrOfCopies = Integer.parseInt(request.getParameter("nrOfCopies"));
        BookHandler.createBook(new BookInfo(isbn, title, genre, author, price, nrOfCopies));
    }

    /**
     * Edits an existing book's data.
     *
     * @param request the {@link HttpServletRequest} containing the updated book data
     */
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

    /**
     * Deletes a book from the system based on the request parameters.
     *
     * @param request the {@link HttpServletRequest} containing the book ID to delete
     */
    private void deleteBook(HttpServletRequest request) {
        String itemIdStr = request.getParameter("itemId");
        BookHandler.deleteBookById(Integer.parseInt(itemIdStr));
    }

    /**
     * Retrieves all books and categories, and forwards the user to the products page.
     *
     * @param request  the {@link HttpServletRequest} containing the request data
     * @param response the {@link HttpServletResponse} used to forward the user to the product page
     * @throws IOException      if an input or output error is detected
     * @throws ServletException if the servlet encounters a processing error
     */
    private void updateAndRedirect(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Collection<BookInfo> booksInfo = BookHandler.getAllBooks();
        request.setAttribute("booksInfo", booksInfo);
        ArrayList<String> genres = BookHandler.getAllCategories();
        request.setAttribute("genres", genres);
        request.getRequestDispatcher("products.jsp").forward(request, response);
    }
}
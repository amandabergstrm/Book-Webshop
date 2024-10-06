package businessObjects;

import ui.view.BookInfo;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Handles the business logic related to books. This class acts as a bridge between the {@link Book} class and
 * the application logic. It provides methods to create, update, delete, and retrieve books, as well as manage categories.
 */
public class BookHandler {

    /**
     * Creates a new book record in the system.
     *
     * @param bookInfo the {@link BookInfo} object containing the details of the new book
     */
    public static void createBook(BookInfo bookInfo) {
        Book bookObj = new Book(bookInfo.getIsbn(), bookInfo.getTitle(), bookInfo.getGenre(), bookInfo.getAuthor(), bookInfo.getNrOfCopies(), bookInfo.getPrice());
        Book.createBook(bookObj);
    }

    /**
     * Retrieves a book by its item ID.
     *
     * @param itemId the ID of the book to retrieve
     * @return a {@link BookInfo} object containing the book details
     */
    public static BookInfo getBookByItemId(int itemId) {
        Book bookObj = Book.searchBookByItemId(itemId);
        return new BookInfo(bookObj.getItemId(), bookObj.getIsbn(), bookObj.getTitle(), bookObj.getGenre(), bookObj.getAuthor(), bookObj.getNrOfCopies(), bookObj.getPrice());
    }

    /**
     * Retrieves all books from the database.
     *
     * @return a collection of {@link BookInfo} objects representing all books
     */
    public static Collection<BookInfo> getAllBooks() {
        Collection c = Book.importAllBooks();
        ArrayList<BookInfo> booksInfo = new ArrayList<>();
        for (Object b : c) {
            Book bookObj = (Book) b;
            booksInfo.add(new BookInfo(bookObj.getItemId(), bookObj.getIsbn(), bookObj.getTitle(), bookObj.getGenre(), bookObj.getAuthor(), bookObj.getNrOfCopies(), bookObj.getPrice()));
        }
        return booksInfo;
    }

    /**
     * Updates the number of copies and price of a book.
     *
     * @param bookInfo       the {@link BookInfo} object containing the original book details
     * @param newNrOfCopies  the new number of copies available
     * @param newPrice       the new price of the book
     */
    public static void updateBook(BookInfo bookInfo, int newNrOfCopies, int newPrice) {
        Book bookObj = new Book(bookInfo.getItemId(), bookInfo.getIsbn(), bookInfo.getTitle(), bookInfo.getGenre(), bookInfo.getAuthor(), newNrOfCopies, newPrice);
        Book.updateBook(bookObj);
    }

    /**
     * Deletes a book by its item ID.
     *
     * @param itemId the ID of the book to delete
     */
    public static void deleteBookById(int itemId) {
        Book.deleteBookById(itemId);
    }

    /**
     * Adds a new category (genre) to the system.
     *
     * @param category the name of the category to add
     */
    public static void addCategory(String category) {
        Book.addCategory(category);
    }

    /**
     * Retrieves all categories (genres) available in the system.
     *
     * @return a list of category names
     */
    public static ArrayList<String> getAllCategories() {
        ArrayList<String> categories = Book.importAllCategories();
        return new ArrayList<>(categories);
    }
}
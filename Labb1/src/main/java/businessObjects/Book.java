package businessObjects;

import database.DbBook;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Represents a Book in the system. Handles book-related operations such as creation, updates, and deletion.
 * Provides validation for ISBN and allows for interaction with the database via {@link DbBook}.
 */
public class Book {

    /** Regex pattern to validate ISBN (12 digits followed by a digit or 'X'). */
    private static final Pattern ISBN_PATTERN = Pattern.compile("^\\d{12}[\\d|X]$");

    /** Unique identifier for the book. */
    private int itemId;

    /** The ISBN of the book. */
    private final String isbn;

    /** The title of the book. */
    private final String title;

    /** The genre of the book. */
    private final String genre;

    /** The author of the book. */
    private final String author;

    /** The number of copies of the book available. */
    private int nrOfCopies;

    /** The price of the book. */
    private int price;

    /**
     * Validates the given ISBN.
     *
     * @param isbn the ISBN to validate
     * @return true if the ISBN is valid, false otherwise
     */
    public static boolean isIsbnValid(String isbn) {
        return ISBN_PATTERN.matcher(isbn).matches();
    }

    /**
     * Inserts a new book record into the database.
     *
     * @param bookObj the book object to be inserted
     */
    public static void createBook(Book bookObj) {
        DbBook.executeBookInsert(bookObj);
    }

    /**
     * Searches for a book in the database by its item ID.
     *
     * @param itemId the ID of the book to search for
     * @return the found book, or null if not found
     */
    public static Book searchBookByItemId(int itemId) {
        return DbBook.searchBookByItemID(itemId);
    }

    /**
     * Imports all books from the database.
     *
     * @return a list of all books
     */
    public static ArrayList<DbBook> importAllBooks() {
        return DbBook.importAllBooks();
    }

    /**
     * Updates an existing book record in the database.
     *
     * @param bookObj the book object containing the updated information
     */
    public static void updateBook(Book bookObj) {
        DbBook.executeBookUpdate(bookObj);
    }

    /**
     * Deletes a book record from the database by its item ID.
     *
     * @param itemId the ID of the book to delete
     */
    public static void deleteBookById(int itemId) {
        DbBook.executeBookRemove(itemId);
    }

    /**
     * Adds a new category to the database.
     *
     * @param category the category to add
     */
    public static void addCategory(String category) {
        DbBook.executeCategoryInsert(category);
    }

    /**
     * Imports all categories from the database.
     *
     * @return a list of all categories
     */
    public static ArrayList<String> importAllCategories() {
        return DbBook.importAllCategories();
    }

    /**
     * Constructs a new Book object.
     *
     * @param isbn        the ISBN of the book
     * @param title       the title of the book
     * @param genre       the genre of the book
     * @param author      the author of the book
     * @param nrOfCopies  the number of copies available
     * @param price       the price of the book
     */
    public Book(String isbn, String title, String genre, String author, int nrOfCopies, int price) {
        if (!isIsbnValid(isbn))
            throw new IllegalArgumentException("Not a valid ISBN");
        this.isbn = isbn;
        this.title = title;
        this.genre = genre;
        this.author = author;
        if (nrOfCopies < 0)
            throw new IllegalArgumentException("Not a valid number of copies");
        this.nrOfCopies = nrOfCopies;
        if (price < 0)
            throw new IllegalArgumentException("Not a valid price");
        this.price = price;
    }

    /**
     * Constructs a new Book object with an item ID.
     *
     * @param itemId      the item ID of the book
     * @param isbn        the ISBN of the book
     * @param title       the title of the book
     * @param genre       the genre of the book
     * @param author      the author of the book
     * @param nrOfCopies  the number of copies available
     * @param price       the price of the book
     */
    public Book(int itemId, String isbn, String title, String genre, String author, int nrOfCopies, int price) {
        this(isbn, title, genre, author, nrOfCopies, price);
        this.itemId = itemId;
    }

    public int getItemId() {
        return itemId;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getAuthor() {
        return author;
    }

    public int getNrOfCopies() {
        return nrOfCopies;
    }

    public int getPrice() {
        return price;
    }

    /**
     * Updates the number of copies for the book.
     *
     * @param nrOfCopies the new number of copies
     * @throws IllegalArgumentException if the number of copies is negative
     */
    public void setNrOfCopies(int nrOfCopies) {
        if (nrOfCopies < 0)
            throw new IllegalArgumentException("Not a valid number of copies");
        this.nrOfCopies = nrOfCopies;
    }

    /**
     * Updates the price of the book.
     *
     * @param price the new price
     * @throws IllegalArgumentException if the price is negative
     */
    public void setPrice(int price) {
        if (price < 0)
            throw new IllegalArgumentException("Not a valid price");
        this.price = price;
    }

    /**
     * Returns a string representation of the book.
     *
     * @return a string describing the book
     */
    @Override
    public String toString() {
        return "Book {" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", author='" + author + '\'' +
                ", nrOfCopies=" + nrOfCopies +
                ", price=" + price +
                '}';
    }
}
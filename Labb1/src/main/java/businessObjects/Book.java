package businessObjects;

import database.DbBook;

import java.util.Collection;
import java.util.regex.Pattern;

public class Book {
    private static final Pattern ISBN_PATTERN = Pattern.compile("^\\d{12}[\\d|X]$");
    private int itemId;
    private final String isbn;
    private final String title;
    private final Genre genre;
    private final String author;
    private int nrOfCopies;
    private int price;

    public static boolean isIsbnValid(String isbn) {
        return ISBN_PATTERN.matcher(isbn).matches();
    }

    public static void createBook(Book book) {
        DbBook.executeBookInsert(book);
    }

    public static Book searchBookByItemId(int itemId) {
        return DbBook.searchBookByItemID(itemId);
    }

    public static Collection importAllBooks() {
      return DbBook.importAllBooks();
    }

    public static void updateBook(Book book) {
        DbBook.executeBookUpdate(book);
    }

    public static void deleteBookById(int itemId) {
        DbBook.executeBookRemove(itemId);
    }

    public Book(String isbn, String title, Genre genre, String author, int nrOfCopies, int price) {
        if (!isIsbnValid(isbn))
            throw new IllegalArgumentException("Not a valid isbn");
        this.isbn = isbn;
        this.title = title;
        this.genre = genre;
        this.author = author;
        if (nrOfCopies < 0)
            throw new IllegalArgumentException("Not valid number of copies");
        this.nrOfCopies = nrOfCopies;
        if (price < 0)
            throw new IllegalArgumentException("Not valid price");
        this.price = price;
    }

    public Book(int itemId, String isbn, String title, Genre genre, String author, int nrOfCopies, int price) {
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

    public Genre getGenre() {
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

    public void setNrOfCopies(int nrOfCopies) {
        if (nrOfCopies < 0)
            throw new IllegalArgumentException("Not valid number of copies");
        this.nrOfCopies = nrOfCopies;
    }

    public void setPrice(int price) {
        if (price < 0)
            throw new IllegalArgumentException("Not valid price");
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book {" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", genre=" + genre +
                ", author='" + author + '\'' +
                ", nrOfCopies=" + nrOfCopies +
                ", price=" + price +
                '}';
    }
}
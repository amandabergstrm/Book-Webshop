package ui.view;

/**
 * The {@code BookInfo} class represents the information of a book in the system.
 * It encapsulates details such as the ISBN, title, genre, author, number of copies, and price.
 */
public class BookInfo {
    private int itemId;
    private final String isbn;
    private final String title;
    private final String genre;
    private final String author;
    private int nrOfCopies;
    private int price;

    /**
     * Constructs a {@code BookInfo} object with the provided book details (without item ID).
     *
     * @param isbn       the ISBN of the book
     * @param title      the title of the book
     * @param genre      the genre of the book
     * @param author     the author of the book
     * @param nrOfCopies the number of copies available
     * @param price      the price of the book
     */
    public BookInfo(String isbn, String title, String genre, String author, int nrOfCopies, int price) {
        this.isbn = isbn;
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.nrOfCopies = nrOfCopies;
        this.price = price;
    }

    /**
     * Constructs a {@code BookInfo} object with the provided book details, including the item ID.
     *
     * @param itemId     the unique identifier of the book
     * @param isbn       the ISBN of the book
     * @param title      the title of the book
     * @param genre      the genre of the book
     * @param author     the author of the book
     * @param nrOfCopies the number of copies available
     * @param price      the price of the book
     */
    public BookInfo(int itemId, String isbn, String title, String genre, String author, int nrOfCopies, int price) {
        this(isbn, title, genre, author, nrOfCopies, price);
        this.itemId = itemId;
    }

    // Getters
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

    // Setters
    public void setNrOfCopies(int nrOfCopies) {
        this.nrOfCopies = nrOfCopies;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
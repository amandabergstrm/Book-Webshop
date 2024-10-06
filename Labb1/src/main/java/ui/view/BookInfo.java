package ui.view;

public class BookInfo {
    private int itemId;
    private final String isbn;
    private final String title;
    private final String genre;
    private final String author;
    private int nrOfCopies;
    private int price;

    public BookInfo(String isbn, String title, String genre, String author, int nrOfCopies, int price) {
        this.isbn = isbn;
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.nrOfCopies = nrOfCopies;
        this.price = price;
    }

    public BookInfo(int itemId, String isbn, String title, String genre, String author, int nrOfCopies, int price) {
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

    public void setNrOfCopies(int nrOfCopies) {
        this.nrOfCopies = nrOfCopies;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
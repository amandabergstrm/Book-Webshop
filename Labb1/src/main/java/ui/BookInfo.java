package ui;

import businessObjects.Genre;

public class BookInfo {
    private final String isbn;
    private final String title;
    private final Genre genre;
    private final String author;
    private int nrOfCopies;

    public BookInfo(String isbn, String title, Genre genre, String author, int nrOfCopies) {
        this.isbn = isbn;
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.nrOfCopies = nrOfCopies;
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

    public void setNrOfCopies(int nrOfCopies) {
        this.nrOfCopies = nrOfCopies;
    }
}